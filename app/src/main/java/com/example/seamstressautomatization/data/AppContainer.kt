package com.example.seamstressautomatization.data

import com.example.seamstressautomatization.data.databases.StuffDatabase
import com.example.seamstressautomatization.data.repositories.OfflineStuffRepository
import com.example.seamstressautomatization.data.repositories.StuffRepository
import android.content.Context

interface AppContainer {
    val stuffRepository: StuffRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val stuffRepository: StuffRepository by lazy {
        OfflineStuffRepository(StuffDatabase.getDatabase(context).stuffDao())
    }
}