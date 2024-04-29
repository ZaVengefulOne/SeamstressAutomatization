package com.example.seamstressautomatization.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seamstressautomatization.data.DAOs.ClothesDao
import com.example.seamstressautomatization.data.DAOs.StuffDao
import com.example.seamstressautomatization.data.entities.Cloth
import com.example.seamstressautomatization.data.entities.Stuff
import com.example.seamstressautomatization.data.utilities.Converters

@Database(entities = [Cloth::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
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