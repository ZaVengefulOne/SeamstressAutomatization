package com.example.seamstressautomatization.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seamstressautomatization.data.DAOs.OperationDao
import com.example.seamstressautomatization.data.entities.Operation

@Database(entities = [Operation::class], version = 1, exportSchema = false)
abstract class OperationsDatabase: RoomDatabase() {
    abstract fun operationsDao(): OperationDao

    companion object {

        private var Instance: OperationsDatabase? = null

        fun getDatabase(context: Context): OperationsDatabase {
            synchronized(this) {
                var instance = Instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OperationsDatabase::class.java,
                        "operations"
                    ).fallbackToDestructiveMigration()
                        .build()
                    Instance = instance
                }
                return instance
            }
        }
    }
}