package com.example.seamstressautomatization.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.seamstressautomatization.data.DAOs.ClothesDao
import com.example.seamstressautomatization.data.DAOs.PartsDao
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Part
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PartsRepository(private val partsDao: PartsDao) {
    val allParts: LiveData<List<Part>> = partsDao.getAllItems()
    val searchResults = MutableLiveData<List<Part>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertPart(newPart: Part){
        coroutineScope.launch (Dispatchers.IO){
            partsDao.insert(newPart)
        }
    }

    fun deletePart(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            partsDao.delete(name)
        }
    }

    fun findPart(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    fun deleteAll(){
        coroutineScope.launch(Dispatchers.IO) {
            partsDao.deleteAll()
        }
    }

    private fun asyncFind(name: String): Deferred<List<Part>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async partsDao.getItem(name)
        }
}