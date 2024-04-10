package com.example.seamstressautomatization.Screens

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.text.Placeholder
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
            .background(colorScheme.primary)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(head1, color = colorScheme.surface,
            modifier = Modifier
                .weight(0.1f))
        Text(head2, color = colorScheme.surface,
            modifier = Modifier
                .weight(0.2f))
        Text(head3, color = colorScheme.surface,
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
        OutlinedCard(shape = CardDefaults.elevatedShape, border = BorderStroke(0.5.dp,Color.Black)
        ) {
           Row {
               Text(id.toString(), modifier = Modifier
                   .weight(0.1f))
               Text(name, modifier = Modifier.weight(0.2f))
               Text(salary.toString(), modifier = Modifier.weight(0.2f))
           }
        }
    }
}

@Composable
fun CustomTextField(
    title: String,
    textState: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType,
    placeholder: String,
) {
    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        label = { Text(title)},
        modifier = Modifier.padding(16.dp),
        placeholder = {placeholder},
        colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.DarkGray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                ),
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {if (textState.isNotEmpty()) IconButton(onClick = { onTextChange("") }) {
        Icon(Icons.Filled.Clear, contentDescription = null)
        } else null}
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

    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column (verticalArrangement = Arrangement.Bottom, horizontalAlignment = CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .weight(12f)){
            CustomTextField(
                title = "Имя",
                textState = stuffName,
                onTextChange = onStuffNameChange,
                keyboardType = KeyboardType.Text,
                placeholder = "Введите имя..."
            )

            CustomTextField(
                title = "Зарплата",
                textState = stuffSalary,
                onTextChange = onStuffSalaryChange,
                keyboardType = KeyboardType.Number,
                placeholder = "Введите зарплату..."
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .weight(1f)
        ) {
            IconButton(onClick = {
                if (stuffSalary.isNotEmpty()) {
                    viewModel.insertStuff(
                        Stuff(
                            viewModel.id_count,
                            stuffName,
                            stuffSalary.toInt()
                        )
                    )
                    searching = false
                    viewModel.id_count++
                }
            }) {
                Icon(Icons.Filled.Add, contentDescription = "add stuff")
            }

            IconButton(onClick = {
                searching = true
                viewModel.findStuff(stuffName)
            }) {
                Icon(Icons.Filled.Search, contentDescription = "search stuff")
            }

            IconButton(onClick = {
                searching = false
                viewModel.deleteStuff(stuffName)
                viewModel.id_count--
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "delete stuff")
            }

            IconButton(onClick = {
                searching = false
                stuffName = ""
                stuffSalary = ""
            }) {
                Icon(Icons.Filled.Clear, contentDescription = "clear field")
            }
            
            Button(onClick = {
                viewModel.deleteAll()
                viewModel.id_count = 0
                             }, colors = ButtonDefaults.buttonColors(backgroundColor = colorScheme.primary)) {
                Text(text = "Очистить таблицу", color = Color.Red, fontSize = 10.sp)
            }
        }
    }
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        val list = if (searching) searchResults else allStuff
        item {
            TitleRow(head1 = "Номер", head2 = "Имя", head3 = "Зарплата")
        }
        items(list) { stuff ->
            StuffRow(
                id = stuff.id, name = stuff.stuff_name,
                salary = stuff.salary
            )
        }
    }
}

class StuffViewModelFactory(val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StuffViewModel(application) as T
    }
}






