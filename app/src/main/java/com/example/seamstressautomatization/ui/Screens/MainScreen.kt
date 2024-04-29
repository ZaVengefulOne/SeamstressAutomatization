package com.example.seamstressautomatization.ui.Screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.seamstressautomatization.data.entities.Stuff
import com.example.seamstressautomatization.ui.Screens.utilityFuns.CustomTextField
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TableRow
import com.example.seamstressautomatization.ui.Screens.utilityFuns.TitleRow
import com.example.seamstressautomatization.ui.theme.Dimens
import com.example.seamstressautomatization.ui.viewmodels.HomeViewModel
import com.example.seamstressautomatization.utilities.Constants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Main(navController: NavController, homeViewModel: HomeViewModel) {
    val allStuff by homeViewModel.allStuff.observeAsState(listOf())
    val allOperations by homeViewModel.allOperations.observeAsState(listOf())
    val context = LocalContext.current
    var isMenuVisible by remember { mutableStateOf(false) }
    var enteredValue by remember { mutableIntStateOf(0)}
    var selectedStuffName by remember { mutableStateOf(String())}
    var selectedOperationName by remember { mutableStateOf(String())}
    var updatingStuffId by remember { mutableIntStateOf(0)}
    var updatingPartModifier by remember { mutableIntStateOf(0) }
    var baseSalary by remember { mutableFloatStateOf(0f) }
    var timeAmount by remember { mutableIntStateOf(0) }
    var nameExpanded by remember {
        mutableStateOf(false)
    }
    var operationsExpanded by remember {
        mutableStateOf(false)
    }
    var amountExpanded by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .fillMaxSize()) {
        Text(text = "Главная страница", modifier = Modifier.align(Alignment.CenterHorizontally))
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            val list = allStuff
            item {
                TitleRow(head1 = "Номер", head2 = "Имя", head3 = "Зарплата", fontSize = 18.sp)
            }
            items(list) { stuff ->
                TableRow(
                    id = stuff.id, name = stuff.stuff_name,
                    stats = stuff.salary.toFloat(),
                    icon = Icons.Filled.Person
                )
            }
        }
        Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom){
            if (!isMenuVisible) {
                TextButton(
                    onClick = { isMenuVisible = true},
                    Modifier.fillMaxWidth(),
                    enabled = !isMenuVisible
                ) {
                    Row {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp),
                            tint = colorScheme.primary
                        )
                        Text(
                            text = "Добавить выполненное",
                            Modifier.padding(5.dp),
                            color = colorScheme.primary,
                            fontSize = 24.sp
                        )
                    }
                }
            }
            else {
                Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
                    Row {
                        ExposedDropdownMenuBox(expanded = amountExpanded, onExpandedChange = {amountExpanded = !amountExpanded}, modifier = Modifier.weight(0.6f)) {
                            OutlinedTextField(
                                value = enteredValue.toString(),
                                onValueChange = {},
                                label = { Text("Кол-во", fontSize = 10.sp) },
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = nameExpanded
                                    )
                                },
                                modifier = Modifier.menuAnchor(),
                            )
                            ExposedDropdownMenu(
                                expanded = amountExpanded,
                                onDismissRequest = {},
                            ) {
                                Array(100) {it+1}.forEach { item ->
                                    androidx.compose.material3.DropdownMenuItem(
                                        text = { Text(text = item.toString()) },
                                        onClick = {
                                            enteredValue = item
                                            amountExpanded = false
                                        })
                                }
                            }
                        }
                            ExposedDropdownMenuBox(
                                expanded = nameExpanded,
                                onExpandedChange = { nameExpanded = !nameExpanded }, modifier = Modifier
                                    .weight(1.4f)
                                    .padding(8.dp)) {
                                OutlinedTextField(
                                    value = selectedStuffName,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = nameExpanded
                                        )
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .weight(1f)
                                )
                                ExposedDropdownMenu(
                                    expanded = nameExpanded,
                                    onDismissRequest = {},
                                ) {
                                    allStuff.forEach { item ->
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = { Text(text = item.stuff_name) },
                                            onClick = {
                                                selectedStuffName = item.stuff_name
                                                updatingStuffId = item.id
                                                baseSalary = item.salary
                                                nameExpanded = false
                                            })
                                    }
                                }
                            }
                                ExposedDropdownMenuBox(expanded = operationsExpanded, onExpandedChange = { operationsExpanded = !operationsExpanded}, modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)) {
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
                                            androidx.compose.material3.DropdownMenuItem(
                                                text = { Text(text = item.operation_name) },
                                                onClick = {
                                                    selectedOperationName = item.operation_name
                                                    updatingPartModifier = item.duration_sec
                                                    nameExpanded = false
                                                })
                                        }
                                    }
                                }


                    }
                    Row {

                        Button(onClick = {
                            homeViewModel.updateStuff(
                                Stuff(
                                    updatingStuffId,
                                    selectedStuffName,
                                    (baseSalary + (updatingPartModifier * enteredValue))
                                )
                            )
                            isMenuVisible = false
                            selectedOperationName = ""
                            enteredValue = 0
                            selectedStuffName = ""
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = colorScheme.primary)) {
                            Text("Готово", color = colorScheme.surface )
                        }
                        Spacer(modifier = Modifier.padding(110.dp))
//                        OutlinedTextField(value = timeAmount, onValueChange = {timeAmount = it})
                        Button(onClick = {  isMenuVisible = false }, colors = ButtonDefaults.buttonColors(backgroundColor = colorScheme.primary)) {
                            Text(text = "Назад", color = colorScheme.surface)
                        }
                    }
            }
            }
        }
        }
}



@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Constants.BottomNavItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label, tint = MaterialTheme.colorScheme.surface)
                },
                label = {
                    androidx.compose.material3.Text(text = navItem.label, fontSize = Dimens.textSizeSmall, color = MaterialTheme.colorScheme.surface)
                },
                alwaysShowLabel = false
            )
        }
    }
}

class MainViewModelFactory(val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(application) as T
    }
}