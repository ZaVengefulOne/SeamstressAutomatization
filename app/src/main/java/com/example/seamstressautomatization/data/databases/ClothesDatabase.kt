package com.example.seamstressautomatization.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seamstressautomatization.data.DAOs.ClothesDao
import com.example.seamstressautomatization.data.DAOs.StuffDao
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Stuff

@Database(entities = [Cloth::class], version = 1, exportSchema = false)
abstract class ClothesDatabase : RoomDatabase() {
    abstract fun clothesDao(): ClothesDao

    companion object {

        private var Instance: ClothesDatabase? = null

        fun getDatabase(context: Context): ClothesDatabase {
            synchronized(this) {
                var instance = Instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ClothesDatabase::class.java,
                        "clothes"
                    ).fallbackToDestructiveMigration()
                        .build()
                    Instance = instance
                }
                return instance
            }
        }
    }
}