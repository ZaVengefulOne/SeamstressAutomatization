package com.example.seamstressautomatization.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
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

class HomeViewModel(application: Application) : ViewModel() {
    private val stuffRepository : StuffRepository
    private val clothesRepository: ClothesRepository
    private val operationsRepository: OperationsRepository
    val allStuff: LiveData<List<Stuff>>
    val allOperations: LiveData<List<Operation>>
    val allClothes: LiveData<List<Cloth>>
    init {
    val stuffDb = StuffDatabase.getDatabase(application)
        val stuffDao = stuffDb.stuffDao()
        val clothDb = ClothesDatabase.getDatabase(application)
        val clothDao = clothDb.clothesDao()
        val operationsDb = OperationsDatabase.getDatabase(application)
        val operationsDao = operationsDb.operationsDao()
        stuffRepository = StuffRepository(stuffDao)
        clothesRepository = ClothesRepository(clothDao)
        operationsRepository = OperationsRepository(operationsDao)
        allStuff = stuffRepository.allStuff
        allOperations = operationsRepository.allOperations
        allClothes = clothesRepository.allClothes
}
    fun findStuff(name: String){
        stuffRepository.findStuff(name)
    }

    fun deleteStuff(name: String){
        stuffRepository.deleteStuff(name)
    }

    fun insertStuff(newStuff: Stuff){
        stuffRepository.insertStuff(newStuff)
    }

    fun updateStuff(stuff: Stuff){
        stuffRepository.updateStuff(stuff)
    }
}