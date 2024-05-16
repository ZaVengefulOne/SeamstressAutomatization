package com.example.seamstressautomatization.ui.Screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Operation
import com.example.seamstressautomatization.data.entities.Stuff
import com.example.seamstressautomatization.ui.Screens.utilityFuns.CustomTextField
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TableRow
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TitleRow
import com.example.seamstressautomatization.ui.viewmodels.ClothesViewModel

@Composable
fun ClothSetup (viewModel: ClothesViewModel)
{
    val allClothes by viewModel.allClothes.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())
    val allOperations by viewModel.allOperations.observeAsState(listOf())
    Clothes(
        allClothes = allClothes,
        allOperations = allOperations,
        searchResults = searchResults,
        viewModel = viewModel
    )
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Clothes(allClothes: List<Cloth>, allOperations: List<Operation>, searchResults: List<Cloth>, viewModel: ClothesViewModel) {
    var clothName by remember {
        mutableStateOf("")
    }
    var chosenOperations = remember {
        mutableListOf<String>()
    }
    var searching by remember {
        mutableStateOf(false)
    }
    val onClothNameChange = { text: String -> clothName = text }

    var isMenuVisible by remember { mutableStateOf(false) }
    var isOperationSet by remember { mutableStateOf(false) }
    var operationsAmount by remember { mutableIntStateOf(0)}
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
    if(!isMenuVisible)
    {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            val list = if (searching) searchResults else allClothes
            item {
                TitleRow(head1 = "Номер", head2 = "Название", head3 = "Список операций", fontSize = 18.sp)
            }
            items(list) { cloth ->
                TableRow(
                    id = cloth.cloth_id, name = cloth.cloth_name,
                    stats = cloth.opertaions_list.size.toFloat(),
                    icon = ImageVector.vectorResource(R.drawable.baseline_checkroom_24)
                )
            }
        }
        Column (verticalArrangement = Arrangement.Bottom) {

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .weight(1f)
            ) {
                IconButton(onClick = {
                    isMenuVisible = true
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "add cloth")
                }

                IconButton(onClick = {
                    searching = true
                    viewModel.findCloth(clothName)
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "search clothes")
                }

                IconButton(onClick = {
                    searching = false
                    viewModel.deleteCloth(clothName)
                    viewModel.id_count--
                }) {
                    Icon(Icons.Filled.Delete, contentDescription = "delete cloth")
                }

                IconButton(onClick = {
                    searching = false
                    clothName = ""
                    chosenOperations = mutableListOf<String>()
                }) {
                    Icon(Icons.Filled.Clear, contentDescription = "clear field")
                }

                Button(
                    onClick = {
                        viewModel.deleteAll()
                        viewModel.id_count = 0
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Очистить таблицу", color = Color.Red, fontSize = 10.sp)
                }
            }
        }
} else {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(12f)
        ) {
            CustomTextField(
                title = "Название",
                textState = clothName,
                onTextChange = onClothNameChange,
                keyboardType = KeyboardType.Text,
                placeholder = "Введите название..."
            )
            Row {
                IconButton(onClick = { operationsAmount++ }) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
                IconButton(onClick = { operationsAmount-- }) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }
            LazyColumn {
               items(count = operationsAmount) {
                   var operationsExpanded by remember { mutableStateOf(false)}
                   var selectedOperationName by remember { mutableStateOf(String())}
                   ExposedDropdownMenuBox(
                       expanded = operationsExpanded,
                       onExpandedChange = { operationsExpanded = !operationsExpanded },
                       modifier = Modifier
                           .weight(1f)
                           .padding(8.dp)
                   ) {
                       OutlinedTextField(
                           value = selectedOperationName,
                           onValueChange = {},
                           readOnly = true,
                           trailingIcon = {
                               ExposedDropdownMenuDefaults.TrailingIcon(
                                   expanded = operationsExpanded
                               )
                           },
                           modifier = Modifier
                               .menuAnchor()
                       )
                       ExposedDropdownMenu(
                           expanded = operationsExpanded,
                           onDismissRequest = {},
                       ) {
                           allOperations.forEach { item ->
                               DropdownMenuItem(
                                   text = { Text(text = item.operation_name) },
                                   onClick = {
                                       //selectedOperationId = item.operation_id
                                       selectedOperationName = item.operation_name
                                       //selectedOperationTime = item.duration_sec.toString()
                                       operationsExpanded = false
                                       chosenOperations.add(selectedOperationName)
                                   })
                           }
                       }
                   }
               }
            }
            Row {

                Button(onClick = {
                    if (clothName.isNotEmpty()) {
                    viewModel.insertCloth(
                        Cloth(
                            viewModel.id_count,
                            clothName,
                            chosenOperations
                        )
                    )
                    searching = false
                        chosenOperations = mutableListOf<String>()
                    viewModel.id_count++
                        clothName = ""
                }
                    isMenuVisible = false
                }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary)) {
                    Text("Готово", color = MaterialTheme.colorScheme.surface )
                }
                Spacer(modifier = Modifier.padding(110.dp))
//                        OutlinedTextField(value = timeAmount, onValueChange = {timeAmount = it})
                Button(onClick = {  isMenuVisible = false }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary)) {
                    Text(text = "Назад", color = MaterialTheme.colorScheme.surface)
                }
            }
        }
    }
    }

}

class ClothViewModelFactory(val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClothesViewModel(application) as T
    }
}