package com.example.seamstressautomatization.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.seamstressautomatization.ui.theme.primaryGreen
import com.example.seamstressautomatization.utilities.Stuff

@Composable
fun Main(navController: NavController){
    val context = LocalContext.current
    Column (modifier = Modifier.fillMaxSize()){
        Text(text = "Главная страница", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun Stuff(){
    // Just a fake data... a Pair of Int and String
//    val tableData = (2..100).mapIndexed { index, item ->
//        index to "Item $index"
//    }
//    val numbers = arrayOf(1,2,3,4,5)
//    val names = arrayOf("Нагора", "Гулькаё", "Ином", "Шукур", "Азам")
//    val tableData = arrayOf(Pair(numbers[0],names[0]), Pair(numbers[1],names[1]), Pair(numbers[2],names[2]),
//        Pair(numbers[3],names[3]), Pair(numbers[4],names[4]))
    val context = LocalContext.current
    val stuff_num = remember{ mutableIntStateOf(0) }
    stuff_num.intValue = 5
    var tableData = mutableListOf(Stuff(1, "Нагора",), Stuff(2, "Гулькаё"), Stuff(3, "Ином"), Stuff(4, "Шукур"), Stuff(5, "Азам"))
    var name_input = remember{mutableStateOf("")}
    // Each cell of a column must have the same weight.
    val column1Weight = .2f // 20%
    val column2Weight = .8f // 80%
    var isInputOpened = remember { mutableStateOf(false)}
    // The LazyColumn will be our table. Notice the use of the weights below
    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){
        Row {

            IconButton( modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {Toast.makeText(context,isInputOpened.value.toString(),Toast.LENGTH_LONG).show()
                          isInputOpened.value = !isInputOpened.value},
                enabled = !isInputOpened.value
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
            TextField(value = "", label = {Text(text="Имя сотрудника")}, trailingIcon = { Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null, modifier = Modifier.clickable {
                    stuff_num.intValue += 1
                    tableData.add(Stuff(stuff_num.intValue, name_input.value))
                    Log.i("Stuff composition:", tableData.toString()) }
            ) }, onValueChange = {
                newName -> name_input.value = newName
            }, enabled = !isInputOpened.value, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            // Here is the header
            item {
                Row(Modifier.background(primaryGreen)) {
                    TableCell(text = "Номер", weight = .17f)
                    TableCell(text = "Имя", weight = .83f)
                }
            }
            // Here are all the lines of your table.
            items(tableData) {
                val id = it.id
                val name = it.stuff_name
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = id.toString(), weight = column1Weight)
                    TableCell(text = name, weight = column2Weight)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Create, contentDescription = "Edit")
                    }
                }
            }
        }
    }
}
@Composable
fun Clothes(){
    val context = LocalContext.current
    val stuff_num = remember{ mutableIntStateOf(0) }
    stuff_num.intValue = 5
    var tableData = mutableListOf(Stuff(1, "Пальто",), Stuff(2, "Куртка"), Stuff(3, "Брюки"), Stuff(4, "Штаны"))
    var name_input = remember{mutableStateOf("")}
    // Each cell of a column must have the same weight.
    val column1Weight = .2f // 20%
    val column2Weight = .8f // 80%
    var isInputOpened = remember { mutableStateOf(false)}
    // The LazyColumn will be our table. Notice the use of the weights below
    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){
        Row {

            IconButton( modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {Toast.makeText(context,isInputOpened.value.toString(),Toast.LENGTH_LONG).show()
                    isInputOpened.value = !isInputOpened.value},
                enabled = !isInputOpened.value
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
            TextField(value = "", label = {Text(text="Название элемента одежды")}, trailingIcon = { Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null, modifier = Modifier.clickable {
                    stuff_num.intValue += 1
                    tableData.add(Stuff(stuff_num.intValue, name_input.value))
                    Log.i("Stuff composition:", tableData.toString()) }
            ) }, onValueChange = {
                    newName -> name_input.value = newName
            }, enabled = !isInputOpened.value, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            // Here is the header
            item {
                Row(Modifier.background(primaryGreen)) {
                    TableCell(text = "Номер", weight = .17f)
                    TableCell(text = "Название", weight = .83f)
                }
            }
            // Here are all the lines of your table.
            items(tableData) {
                val id = it.id
                val name = it.stuff_name
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = id.toString(), weight = column1Weight)
                    TableCell(text = name, weight = column2Weight)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Create, contentDescription = "Edit")
                    }
                }
            }
        }
    }
}
@Composable
fun Parts(){
    val context = LocalContext.current
    val stuff_num = remember{ mutableIntStateOf(0) }
    stuff_num.intValue = 5
    var tableData = mutableListOf(Stuff(1, "Пуговицы",), Stuff(2, "Борт"), Stuff(3, "Замочек"))
    var name_input = remember{mutableStateOf("")}
    // Each cell of a column must have the same weight.
    val column1Weight = .2f // 20%
    val column2Weight = .8f // 80%
    var isInputOpened = remember { mutableStateOf(false)}
    // The LazyColumn will be our table. Notice the use of the weights below
    Column (modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){
        Row {

            IconButton( modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {Toast.makeText(context,isInputOpened.value.toString(),Toast.LENGTH_LONG).show()
                    isInputOpened.value = !isInputOpened.value},
                enabled = !isInputOpened.value
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
            TextField(value = "", label = {Text(text="Название части")}, trailingIcon = { Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null, modifier = Modifier.clickable {
                    stuff_num.intValue += 1
                    tableData.add(Stuff(stuff_num.intValue, name_input.value))
                    Log.i("Stuff composition:", tableData.toString()) }
            ) }, onValueChange = {
                    newName -> name_input.value = newName
            }, enabled = !isInputOpened.value, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            // Here is the header
            item {
                Row(Modifier.background(primaryGreen)) {
                    TableCell(text = "Номер", weight = .17f)
                    TableCell(text = "Название", weight = .83f)
                }
            }
            // Here are all the lines of your table.
            items(tableData) {
                val id = it.id
                val name = it.stuff_name
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = id.toString(), weight = column1Weight)
                    TableCell(text = name, weight = column2Weight)
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Create, contentDescription = "Edit")
                    }
                }
            }
        }
    }
}