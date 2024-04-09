package com.example.seamstressautomatization.data.repositories
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.seamstressautomatization.data.DAOs.StuffDao
import com.example.seamstressautomatization.data.entities.Stuff
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StuffRepository(private val stuffDao: StuffDao) {

    val allStuff: LiveData<List<Stuff>> = stuffDao.getAllItems()
    val searchResults = MutableLiveData<List<Stuff>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertStuff(newstuff: Stuff){
        coroutineScope.launch (Dispatchers.IO){
            stuffDao.insert(newstuff)
        }
    }

    fun deleteStuff(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            stuffDao.delete(name)
        }
    }

    fun findStuff(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name: String): Deferred<List<Stuff>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async stuffDao.getItem(name)
        }
}
