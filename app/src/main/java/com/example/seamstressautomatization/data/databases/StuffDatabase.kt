package com.example.seamstressautomatization.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seamstressautomatization.data.DAOs.StuffDao
import com.example.seamstressautomatization.data.entities.Stuff

@Database (entities = [Stuff::class], version = 1, exportSchema = false)
abstract class StuffDatabase : RoomDatabase() {
    abstract fun stuffDao(): StuffDao
    companion object {
        @Volatile
        private var Instance: StuffDatabase? = null

        fun getDatabase(context: Context): StuffDatabase {
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, StuffDatabase::class.java, "stuff_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }

            }
        }
    }
}