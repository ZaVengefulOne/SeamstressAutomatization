package com.example.seamstressautomatization.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.seamstressautomatization.data.databases.OperationsDatabase
import com.example.seamstressautomatization.data.entities.Operation
import com.example.seamstressautomatization.data.repositories.OperationsRepository

class OperationsViewModel (application: Application) : ViewModel() {
    val allOperations: LiveData<List<Operation>>
    private val repository : OperationsRepository
    val searchResults: MutableLiveData<List<Operation>>
    var id_count = 0
    init {
        val operationDb = OperationsDatabase.getDatabase(application)
        val operationDao = operationDb.operationsDao()
        repository = OperationsRepository(operationDao)

        allOperations = repository.allOperations
        searchResults = repository.searchResults
    }

    fun insertOperation(operation: Operation){
        repository.insertOperation(operation)
    }

    fun findOperation(name: String){
        repository.findOperation(name)
    }

    fun deleteOperation(name: String){
        repository.deleteOperation(name)
    }

    fun deleteAll(){
        repository.deleteAll()
    }
}