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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.ui.Screens.utilityFuns.CustomTextField
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TableRow
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TitleRow
import com.example.seamstressautomatization.ui.viewmodels.ClothesViewModel

@Composable
fun ClothSetup (viewModel: ClothesViewModel)
{
    val allClothes by viewModel.allClothes.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    Clothes(
        allClothes = allClothes,
        searchResults = searchResults,
        viewModel = viewModel
    )
}



@Composable
fun Clothes(allClothes: List<Cloth>, searchResults: List<Cloth>, viewModel: ClothesViewModel) {
    var clothName by remember {
        mutableStateOf("")
    }
    var clothBasePayment by remember {
        mutableStateOf("")
    }
    var searching by remember {
        mutableStateOf(false)
    }
    val onClothNameChange = { text: String -> clothName = text }
    val onClothBasePaymentChange = { text: String -> clothBasePayment = text }

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
                textState = clothName,
                onTextChange = onClothNameChange,
                keyboardType = KeyboardType.Text,
                placeholder = "Введите название..."
            )

            CustomTextField(
                title = "Базовая стоимость",
                textState = clothBasePayment,
                onTextChange = onClothBasePaymentChange,
                keyboardType = KeyboardType.Number,
                placeholder = "Введите стоимость..."
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
                if (clothBasePayment.isNotEmpty()) {
                    viewModel.insertCloth(
                        Cloth(
                            viewModel.id_count,
                            clothName,
                            clothBasePayment.toFloat()
                        )
                    )
                    searching = false
                    viewModel.id_count++
                }
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
                clothBasePayment = ""
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
        val list = if (searching) searchResults else allClothes
        item {
            TitleRow(head1 = "Номер", head2 = "Название", head3 = "Стоимость")
        }
        items(list) { cloth ->
            TableRow(
                id = cloth.cloth_id, name = cloth.cloth_name,
                stats = cloth.base_cloth_payment
            )
        }
    }
}

class ClothViewModelFactory(val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ClothesViewModel(application) as T
    }
}