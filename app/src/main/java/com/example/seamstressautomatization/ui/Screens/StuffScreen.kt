package com.example.seamstressautomatization.Screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seamstressautomatization.MainActivity
import com.example.seamstressautomatization.R
import com.example.seamstressautomatization.data.entities.Stuff
import com.example.seamstressautomatization.ui.Screens.utilityFuns.StuffItem
import com.example.seamstressautomatization.ui.viewmodels.MainViewModel
import com.example.seamstressautomatization.ui.viewmodels.StuffViewModel


@Composable
fun StuffSetup(viewModel: StuffViewModel)
{
    val allStuff by viewModel.allStuff.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    com.example.seamstressautomatization.Screens.Stuff(
        allStuff = allStuff,
        searchResults = searchResults,
        viewModel = viewModel
    )
}

@Composable
fun TitleRow(head1: String, head2: String, head3: String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(head1, color = Color.White,
            modifier = Modifier
                .weight(0.1f))
        Text(head2, color = Color.White,
            modifier = Modifier
                .weight(0.2f))
        Text(head3, color = Color.White,
            modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun StuffRow(id: Int, name: String, salary: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(id.toString(), modifier = Modifier
            .weight(0.1f))
        Text(name, modifier = Modifier.weight(0.2f))
        Text(salary.toString(), modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun CustomTextField(
    title: String,
    textState: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        label = { Text(title)},
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 30.sp)
    )
}

@Composable
fun Stuff(allStuff: List<Stuff>, searchResults: List<Stuff>, viewModel: StuffViewModel) {
    var stuffName by remember {
        mutableStateOf("")
    }
    var stuffSalary by remember {
        mutableStateOf("")
    }
    var searching by remember {
        mutableStateOf(false)
    }
    val onStuffNameChange = { text: String -> stuffName = text }
    val onStuffSalaryChange = { text: String -> stuffSalary = text }
    var id_count = 0
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomTextField(
            title = "Имя",
            textState = stuffName,
            onTextChange = onStuffNameChange,
            keyboardType = KeyboardType.Text
        )

        CustomTextField(
            title = "Зарплата",
            textState = stuffSalary,
            onTextChange = onStuffSalaryChange,
            keyboardType = KeyboardType.Number
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Button(onClick = {
                if (stuffSalary.isNotEmpty()) {
                    viewModel.insertStuff(
                        Stuff(
                            id_count,
                            stuffName,
                            stuffSalary.toInt()
                        )
                    )
                    searching = false
                    id_count++
                }
            }) {
                Text("Add")
            }

            Button(onClick = {
                searching = true
                viewModel.findStuff(stuffName)
            }) {
                Text("Search")
            }

            Button(onClick = {
                searching = false
                viewModel.deleteStuff(stuffName)
                id_count--
            }) {
                Text("Delete")
            }

            Button(onClick = {
                searching = false
                stuffName = ""
                stuffSalary = ""
            }) {
                Text("Clear")
            }
        }
    }
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val list = if (searching) searchResults else allStuff

        item {
            TitleRow(head1 = "ID", head2 = "Stuff", head3 = "Salary")
        }

        items(list) { stuff ->
            StuffRow(
                id = stuff.id, name = stuff.stuff_name,
                salary = stuff.salary
            )
        }
    }


//    val focusManager = LocalFocusManager.current
//    val stuffAmount = rememberSaveable { mutableIntStateOf(5) }
//    val stuffList = remember {
//        (mutableStateOf(
//            listOf(
//                "Нагора",
//                "Гульнаё",
//                "Ином",
//                "Шукур",
//                "Азам",
//            )
//        )
//                )
//    }
//
//    val searchText = rememberSaveable { mutableStateOf("") }
//    val filteredStuffList = rememberSaveable { mutableStateOf(stuffList.value) }
//    var isSearch = rememberSaveable { mutableStateOf(true) }
//    fun deleteStuff(item: String) {
//        val updatedList = stuffList.value.toMutableList()
//        updatedList.remove(item)
//        stuffList.value = updatedList
//    }
//
//    fun addStuff(item: String) {
//        val updatedList = stuffList.value.toMutableList()
//        updatedList.add(item)
//        stuffList.value = updatedList
//    }
//
//    val trailingIconView = @Composable {
//        androidx.compose.material3.IconButton(onClick = {
//            if (isSearch.value) {
//                searchText.value = ""
//                filteredStuffList.value = stuffList.value
//                focusManager.clearFocus()
//            } else {
//                addStuff(searchText.value)
//                stuffAmount.intValue += 1
//                searchText.value = ""
//                filteredStuffList.value = stuffList.value
//                focusManager.clearFocus()
//            }
//        }) {
//            if (isSearch.value) {
//                androidx.compose.material3.Icon(
//                    Icons.Filled.Close,
//                    contentDescription = "Close Button",
//                    modifier = Modifier.size(25.dp),
//                    tint = Color.Black
//                )
//            } else {
//                androidx.compose.material3.Icon(
//                    Icons.Filled.Add,
//                    contentDescription = "Add Button",
//                    modifier = Modifier.size(25.dp),
//                    tint = Color.Black
//                )
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row {
//            OutlinedTextField(
//                value = searchText.value,
//                onValueChange = { searchText.value = it },
//                modifier = Modifier.padding(16.dp),
//                placeholder = {
//                    androidx.compose.material3.Text(
//                        if (isSearch.value) {
//                            "Поиск..."
//                        } else {
//                            "Добавить..."
//                        },
//                        color =  Color.Black
//                    )
//                },
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = Color.Black,
//                    unfocusedBorderColor = Color.DarkGray,
//                    focusedTextColor = Color.Black,
//                    unfocusedTextColor = Color.DarkGray,
//                ),
//                shape = RoundedCornerShape(16.dp),
//                singleLine = true,
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        if (isSearch.value) {
//                            if (searchText.value.isEmpty()) {
//                                filteredStuffList.value = stuffList.value
//                                focusManager.clearFocus()
//                            } else {
//                                filteredStuffList.value = stuffList.value.filter {
//                                    it.contains(searchText.value, true)
//
//                                }
//                                focusManager.clearFocus()
//                            }
//                        } else {
//                            addStuff(searchText.value)
//                            stuffAmount.intValue += 1
//                            searchText.value = ""
//                            filteredStuffList.value = stuffList.value
//                            focusManager.clearFocus()
//                        }
//                    }
//                ),
//                trailingIcon = if (searchText.value.isNotEmpty()) trailingIconView else null
//            )
//            IconButton(
//                onClick = { isSearch.value = !isSearch.value },
//                modifier = Modifier.padding(20.dp)
//            ) {
//                Icon(
//                    if (isSearch.value) {
//                        Icons.Filled.AddCircle
//                    } else {
//                        Icons.Filled.Search
//                    }, contentDescription = "asda", tint = colorScheme.primary
//                )
//            }
//        }
//        Text(text = "Количество сотрудников: ${stuffAmount.value}", color =  Color.Black)
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Top,
//            content = {
//                items(filteredStuffList.value) { stuffItem ->
//                    StuffItem(stuffItem)
//                }
//            }
//        )
//    }
}

class StuffViewModelFactory(val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StuffViewModel(application) as T
    }
}






