package com.example.seamstressautomatization.ui.Screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Part
import com.example.seamstressautomatization.data.entities.Stuff
import com.example.seamstressautomatization.ui.Screens.utilityFuns.CustomTextField
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TableRow
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TitleRow
import com.example.seamstressautomatization.ui.viewmodels.ClothesViewModel
import com.example.seamstressautomatization.ui.viewmodels.PartsViewModel
import com.example.seamstressautomatization.ui.viewmodels.StuffViewModel


@Composable
fun PartsSetup (viewModel: PartsViewModel)
{
    val allParts by viewModel.allParts.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    Parts(
        allParts = allParts,
        searchResults = searchResults,
        viewModel = viewModel
    )
}

@Composable
fun Parts(allParts: List<Part>, searchResults: List<Part>, viewModel: PartsViewModel) {
    var partName by remember {
        mutableStateOf("")
    }
    var partBasePayment by remember {
        mutableStateOf("")
    }
    var searching by remember {
        mutableStateOf(false)
    }
    val onPartNameChange = { text: String -> partName = text }
    val onPartBasePaymentChange = { text: String -> partBasePayment = text }

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
                textState = partName,
                onTextChange = onPartNameChange,
                keyboardType = KeyboardType.Text,
                placeholder = "Введите название..."
            )

            CustomTextField(
                title = "Базовая стоимость",
                textState = partBasePayment,
                onTextChange = onPartBasePaymentChange,
                keyboardType = KeyboardType.Number,
                placeholder = "Введите базовую стоимость..."
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
                if (partBasePayment.isNotEmpty()) {
                    viewModel.insertPart(
                        Part(
                            viewModel.id_count,
                            partName,
                            partBasePayment.toFloat()
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
                viewModel.findPart(partName)
            }) {
                Icon(Icons.Filled.Search, contentDescription = "search stuff")
            }

            IconButton(onClick = {
                searching = false
                viewModel.deletePart(partName)
                viewModel.id_count--
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "delete stuff")
            }

            IconButton(onClick = {
                searching = false
                partName = ""
                partBasePayment = ""
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
        val list = if (searching) searchResults else allParts
        item {
            TitleRow(head1 = "Номер", head2 = "Название", head3 = "Базовая стоимость")
        }
        items(list) { part ->
            TableRow(
                id = part.part_id, name = part.part_name,
                stats = part.part_base_payment
            )
        }
    }
}

class PartsViewModelFactory(val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PartsViewModel(application) as T
    }
}