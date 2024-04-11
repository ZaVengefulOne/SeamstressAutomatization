package com.example.seamstressautomatization.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.seamstressautomatization.data.databases.ClothesDatabase
import com.example.seamstressautomatization.data.databases.PartsDatabase
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Part
import com.example.seamstressautomatization.data.repositories.ClothesRepository
import com.example.seamstressautomatization.data.repositories.PartsRepository

class PartsViewModel (application: Application) : ViewModel() {
    val allParts: LiveData<List<Part>>
    private val repository : PartsRepository
    val searchResults: MutableLiveData<List<Part>>
    var id_count = 0
    init {
        val partDb = PartsDatabase.getDatabase(application)
        val partDao = partDb.partsDao()
        repository = PartsRepository(partDao)

        allParts = repository.allParts
        searchResults = repository.searchResults
    }

    fun insertPart(part: Part){
        repository.insertPart(part)
    }

    fun findPart(name: String){
        repository.findPart(name)
    }

    fun deletePart(name: String){
        repository.deletePart(name)
    }

    fun deleteAll(){
        repository.deleteAll()
    }
}