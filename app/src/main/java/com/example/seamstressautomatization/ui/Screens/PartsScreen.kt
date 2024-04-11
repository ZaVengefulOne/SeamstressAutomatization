package com.example.seamstressautomatization.ui.Screens

import android.app.Application
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Part
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
    val context = LocalContext.current
    Text(text = "Test")
}

class PartsViewModelFactory(val application: Application):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PartsViewModel(application) as T
    }
}