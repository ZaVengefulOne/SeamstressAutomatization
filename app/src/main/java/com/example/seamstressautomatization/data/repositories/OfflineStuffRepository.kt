package com.example.seamstressautomatization.data.repositories

import com.example.seamstressautomatization.data.DAOs.StuffDao
import com.example.seamstressautomatization.data.entities.Stuff
import kotlinx.coroutines.flow.Flow

class OfflineStuffRepository (private val stuffDao: StuffDao) : StuffRepository {
    override fun getAllStuffStream(): Flow<List<Stuff>> = stuffDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Stuff?> = stuffDao.getItem(id)

    override suspend fun insertStuff(stuff: Stuff) = stuffDao.insert(stuff)

    override suspend fun deleteStuff(stuff: Stuff) = stuffDao.delete(stuff)

    override suspend fun updateStuff(stuff: Stuff) = stuffDao.update(stuff)
}