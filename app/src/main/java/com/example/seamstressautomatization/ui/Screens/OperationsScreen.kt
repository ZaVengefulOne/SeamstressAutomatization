package com.example.seamstressautomatization.ui.Screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seamstressautomatization.R
import com.example.seamstressautomatization.data.entities.Operation
import com.example.seamstressautomatization.ui.Screens.utilityFuns.CustomTextField
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TableRow
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TitleRow
import com.example.seamstressautomatization.ui.viewmodels.OperationsViewModel


@Composable
fun OperationsSetup (viewModel: OperationsViewModel)
{
    val allOperations by viewModel.allOperations.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    Operations(
        allOperations = allOperations,
        searchResults = searchResults,
        viewModel = viewModel
    )
}

@Composable
fun Operations(allOperations: List<Operation>, searchResults: List<Operation>, viewModel: OperationsViewModel) {
    var operationName by remember {
        mutableStateOf("")
    }
    var operationDuration by remember {
        mutableStateOf("")
    }
    var searching by remember {
        mutableStateOf(false)
    }
    val onOperationNameChange = { text: String -> operationName = text }
    val onOperationDurationChange = { text: String -> operationDuration = text }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column (verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
            .weight(12f)){
            CustomTextField(
                title = "Название",
                textState = operationName,
                onTextChange = onOperationNameChange,
                keyboardType = KeyboardType.Text,
                placeholder = "Введите название..."
            )

            CustomTextField(
                title = "Время выполнения (В секундах)",
                textState = operationDuration,
                onTextChange = onOperationDurationChange,
                keyboardType = KeyboardType.Number,
                placeholder = "Введите время выполнения..."
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
                if (operationDuration.isNotEmpty()) {
                    viewModel.insertOperation(
                        Operation(
                            viewModel.id_count,
                            operationName,
                            operationDuration.toInt()
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
                viewModel.findOperation(operationName)
            }) {
                Icon(Icons.Filled.Search, contentDescription = "search stuff")
            }

            IconButton(onClick = {
                searching = false
                viewModel.deleteOperation(operationName)
                viewModel.id_count--
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "delete stuff")
            }

            IconButton(onClick = {
                searching = false
                operationName = ""
                operationDuration = ""
            }) {
                Icon(Icons.Filled.Clear, contentDescription = "clear field")
            }

            Button(onClick = {
                viewModel.deleteAll()
                viewModel.id_count = 0
            }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary)) {
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
        val list = if (searching) searchResults else allOperations
        item {
            TitleRow(head1 = "Название", head2 = "Время операции(секунды)", head3 = "Время операции(минуты)", fontSize = 14.sp)
        }
        items(list) { operation ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Card(shape = CardDefaults.elevatedShape, backgroundColor = MaterialTheme.colorScheme.tertiary, modifier = Modifier.fillMaxWidth()
                ) {
                    Row (horizontalArrangement = Arrangement.Start){
                        Icon(Icons.Filled.Build, contentDescription = null, modifier = Modifier.background(
                            MaterialTheme.colorScheme.tertiary).weight(0.3f))
//                Text(id.toString(), modifier = Modifier.weight(0.1f), fontSize = tableFont)
                        Text(operation.operation_name, modifier = Modifier.weight(1.3f), fontSize = 14.sp)
                        Text(operation.duration_sec.toString(), modifier = Modifier.weight(0.7f), fontSize = 14.sp)
                        Text("%.2f".format((operation.duration_sec / 60f)), modifier = Modifier.weight(0.7f), fontSize = 14.sp)
                    }
                }
            }

        }
    }
}

class OperationsViewModelFactory(val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OperationsViewModel(application) as T
    }
}