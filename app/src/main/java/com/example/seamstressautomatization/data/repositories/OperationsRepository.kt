package com.example.seamstressautomatization.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.seamstressautomatization.data.DAOs.OperationDao
import com.example.seamstressautomatization.data.entities.Operation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class OperationsRepository(private val operationDao: OperationDao) {
    val allOperations: LiveData<List<Operation>> = operationDao.getAllItems()
    val searchResults = MutableLiveData<List<Operation>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertOperation(newOperation: Operation){
        coroutineScope.launch (Dispatchers.IO){
            operationDao.insert(newOperation)
        }
    }

    fun deleteOperation(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            operationDao.delete(name)
        }
    }

    fun findOperation(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    fun deleteAll(){
        coroutineScope.launch(Dispatchers.IO) {
            operationDao.deleteAll()
        }
    }

    private fun asyncFind(name: String): Deferred<List<Operation>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async operationDao.getItem(name)
        }
}