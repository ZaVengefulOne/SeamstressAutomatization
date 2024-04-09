package com.example.seamstressautomatization.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.seamstressautomatization.R

@Composable
fun Main(navController: NavController) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Главная страница", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun Stuff() {
    val focusManager = LocalFocusManager.current
    val stuffAmount = rememberSaveable { mutableIntStateOf(5) }
    val stuffList = remember {
        (mutableStateOf(
            listOf(
                "Нагора",
                "Гульнаё",
                "Ином",
                "Шукур",
                "Азам",
            )
        )
                )
    }
    val searchText = rememberSaveable { mutableStateOf("") }
    val filteredStuffList = rememberSaveable { mutableStateOf(stuffList.value) }
    var isSearch = rememberSaveable { mutableStateOf(true) }
    fun deleteStuff(item: String) {
        val updatedList = stuffList.value.toMutableList()
        updatedList.remove(item)
        stuffList.value = updatedList
    }

    fun addStuff(item: String) {
        val updatedList = stuffList.value.toMutableList()
        updatedList.add(item)
        stuffList.value = updatedList
    }

    val trailingIconView = @Composable {
        androidx.compose.material3.IconButton(onClick = {
            if (isSearch.value) {
                searchText.value = ""
                filteredStuffList.value = stuffList.value
                focusManager.clearFocus()
            } else {
                addStuff(searchText.value)
                stuffAmount.intValue += 1
                searchText.value = ""
                filteredStuffList.value = stuffList.value
                focusManager.clearFocus()
            }
        }) {
            if (isSearch.value) {
                androidx.compose.material3.Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close Button",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            } else {
                androidx.compose.material3.Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add Button",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier.padding(16.dp),
                placeholder = {
                    androidx.compose.material3.Text(
                        if (isSearch.value) {
                            "Поиск..."
                        } else {
                            "Добавить..."
                        },
                        color =  Color.Black
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.DarkGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                ),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (isSearch.value) {
                            if (searchText.value.isEmpty()) {
                                filteredStuffList.value = stuffList.value
                                focusManager.clearFocus()
                            } else {
                                filteredStuffList.value = stuffList.value.filter {
                                    it.contains(searchText.value, true)

                                }
                                focusManager.clearFocus()
                            }
                        } else {
                            addStuff(searchText.value)
                            stuffAmount.intValue += 1
                            searchText.value = ""
                            filteredStuffList.value = stuffList.value
                            focusManager.clearFocus()
                        }
                    }
                ),
                trailingIcon = if (searchText.value.isNotEmpty()) trailingIconView else null
            )
            IconButton(
                onClick = { isSearch.value = !isSearch.value },
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    if (isSearch.value) {
                        Icons.Filled.AddCircle
                    } else {
                        Icons.Filled.Search
                    }, contentDescription = "asda", tint = colorScheme.primary
                )
            }
        }
        Text(text = "Количество сотрудников: ${stuffAmount.value}", color =  Color.Black)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            content = {
                items(filteredStuffList.value) { topicItem ->
                    TopicItem(topicItem)
                }
            }
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun TopicItem(topicItem: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ), border = BorderStroke(1.dp, Color.Black)
    ) {
        Row {
            Icon(
                painter = painterResource(R.drawable.person),
                contentDescription = "Article Icon",
                modifier = Modifier
                    .size(80.dp)
                    .background(colorScheme.primary),
                tint = colorScheme.surface
                )
            Column {
                androidx.compose.material3.Text(
//                            modifier = Modifier.padding(Dimens.paddingMedium),
                    text = topicItem,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorScheme.surface,
                    textAlign = TextAlign.Start
                )
                androidx.compose.material3.Text(
//                            modifier = Modifier.padding(Dimens.paddingMedium),
                    text = "Статус: Свободен",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = colorScheme.surface,
                    textAlign = TextAlign.Center
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete button", tint = colorScheme.surface)
            }
        }
    }
}

@Composable
fun Clothes() {
    val context = LocalContext.current
    Text(text = "Test")
}

@Composable
fun Parts() {
    val context = LocalContext.current
    Text(text = "Test")
}


