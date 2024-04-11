package com.example.seamstressautomatization.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.seamstressautomatization.data.DAOs.ClothesDao
import com.example.seamstressautomatization.data.DAOs.StuffDao
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Stuff
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ClothesRepository(private val clothesDao: ClothesDao) {

    val allClothes: LiveData<List<Cloth>> = clothesDao.getAllItems()
    val searchResults = MutableLiveData<List<Cloth>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertCloth(newCloth: Cloth){
        coroutineScope.launch (Dispatchers.IO){
            clothesDao.insert(newCloth)
        }
    }

    fun deleteCloth(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            clothesDao.delete(name)
        }
    }

    fun findCloth(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    fun deleteAll(){
        coroutineScope.launch(Dispatchers.IO) {
            clothesDao.deleteAll()
        }
    }

    private fun asyncFind(name: String): Deferred<List<Cloth>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async clothesDao.getItem(name)
        }
}