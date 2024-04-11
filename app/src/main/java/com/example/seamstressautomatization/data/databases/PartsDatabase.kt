package com.example.seamstressautomatization.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seamstressautomatization.data.DAOs.ClothesDao
import com.example.seamstressautomatization.data.DAOs.PartsDao
import com.example.seamstressautomatization.data.entities.Part

@Database(entities = [Part::class], version = 1, exportSchema = false)
abstract class PartsDatabase: RoomDatabase() {
    abstract fun partsDao(): PartsDao

    companion object {

        private var Instance: PartsDatabase? = null

        fun getDatabase(context: Context): PartsDatabase {
            synchronized(this) {
                var instance = Instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PartsDatabase::class.java,
                        "parts"
                    ).fallbackToDestructiveMigration()
                        .build()
                    Instance = instance
                }
                return instance
            }
        }
    }
}