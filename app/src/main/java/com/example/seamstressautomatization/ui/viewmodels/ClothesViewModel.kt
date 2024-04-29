package com.example.seamstressautomatization.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.seamstressautomatization.data.databases.ClothesDatabase
import com.example.seamstressautomatization.data.databases.OperationsDatabase
import com.example.seamstressautomatization.data.databases.StuffDatabase
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Operation
import com.example.seamstressautomatization.data.entities.Stuff
import com.example.seamstressautomatization.data.repositories.ClothesRepository
import com.example.seamstressautomatization.data.repositories.OperationsRepository
import com.example.seamstressautomatization.data.repositories.StuffRepository

class ClothesViewModel (application: Application) : ViewModel(){
        val allClothes: LiveData<List<Cloth>>
        val allOperations: LiveData<List<Operation>>
        private val clothRepository : ClothesRepository
        private val operationsRepository : OperationsRepository
        val searchResults: MutableLiveData<List<Cloth>>
        var id_count = 0
        init {
            val clothDb = ClothesDatabase.getDatabase(application)
            val clothDao = clothDb.clothesDao()
            val operationsDb = OperationsDatabase.getDatabase(application)
            val operationDao = operationsDb.operationsDao()
            clothRepository = ClothesRepository(clothDao)
            operationsRepository = OperationsRepository(operationDao)
            allClothes = clothRepository.allClothes
            searchResults = clothRepository.searchResults
            allOperations = operationsRepository.allOperations
        }

        fun insertCloth(cloth: Cloth){
            clothRepository.insertCloth(cloth)
        }

        fun findCloth(name: String){
            clothRepository.findCloth(name)
        }

        fun findOperation(name: String){
            operationsRepository.findOperation(name)
        }

        fun deleteCloth(name: String){
            clothRepository.deleteCloth(name)
        }

        fun deleteAll(){
            clothRepository.deleteAll()
        }
    }
