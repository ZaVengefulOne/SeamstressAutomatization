package com.example.seamstressautomatization.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.seamstressautomatization.data.databases.ClothesDatabase
import com.example.seamstressautomatization.data.databases.PartsDatabase
import com.example.seamstressautomatization.data.databases.StuffDatabase
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Part
import com.example.seamstressautomatization.data.entities.Stuff
import com.example.seamstressautomatization.data.repositories.ClothesRepository
import com.example.seamstressautomatization.data.repositories.PartsRepository
import com.example.seamstressautomatization.data.repositories.StuffRepository

class HomeViewModel(application: Application) : ViewModel() {
    private val stuffRepository : StuffRepository
    private val clothesRepository: ClothesRepository
    private val partsRepository: PartsRepository
    val allStuff: LiveData<List<Stuff>>
    val allParts: LiveData<List<Part>>
    val allClothes: LiveData<List<Cloth>>
    init {
    val stuffDb = StuffDatabase.getDatabase(application)
        val stuffDao = stuffDb.stuffDao()
        val clothDb = ClothesDatabase.getDatabase(application)
        val clothDao = clothDb.clothesDao()
        val partsDb = PartsDatabase.getDatabase(application)
        val partsDao = partsDb.partsDao()
        stuffRepository = StuffRepository(stuffDao)
        clothesRepository = ClothesRepository(clothDao)
        partsRepository = PartsRepository(partsDao)
        allStuff = stuffRepository.allStuff
        allParts = partsRepository.allParts
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