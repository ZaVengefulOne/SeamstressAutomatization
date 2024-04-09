package com.example.seamstressautomatization.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.seamstressautomatization.data.databases.StuffDatabase
import com.example.seamstressautomatization.data.entities.Stuff
import com.example.seamstressautomatization.data.repositories.StuffRepository

class StuffViewModel(application: Application) : ViewModel() {
    val allStuff: LiveData<List<Stuff>>
    private val repository : StuffRepository
    val searchResults: MutableLiveData<List<Stuff>>

    init {
        val stuffDb = StuffDatabase.getDatabase(application)
        val stuffDao = stuffDb.stuffDao()
        repository = StuffRepository(stuffDao)

        allStuff = repository.allStuff
        searchResults = repository.searchResults
    }

    fun insertStuff(stuff: Stuff){
        repository.insertStuff(stuff)
    }

    fun findStuff(name: String){
        repository.findStuff(name)
    }

    fun deleteStuff(name: String){
        repository.deleteStuff(name)
    }
}