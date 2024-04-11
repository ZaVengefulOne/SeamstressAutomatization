package com.example.seamstressautomatization.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.seamstressautomatization.data.databases.ClothesDatabase
import com.example.seamstressautomatization.data.databases.StuffDatabase
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Stuff
import com.example.seamstressautomatization.data.repositories.ClothesRepository
import com.example.seamstressautomatization.data.repositories.StuffRepository

class ClothesViewModel (application: Application) : ViewModel(){
        val allClothes: LiveData<List<Cloth>>
        private val repository : ClothesRepository
        val searchResults: MutableLiveData<List<Cloth>>
        var id_count = 0
        init {
            val clothDb = ClothesDatabase.getDatabase(application)
            val clothDao = clothDb.clothesDao()
            repository = ClothesRepository(clothDao)

            allClothes = repository.allClothes
            searchResults = repository.searchResults
        }

        fun insertCloth(cloth: Cloth){
            repository.insertCloth(cloth)
        }

        fun findCloth(name: String){
            repository.findCloth(name)
        }

        fun deleteCloth(name: String){
            repository.deleteCloth(name)
        }

        fun deleteAll(){
            repository.deleteAll()
        }
    }
