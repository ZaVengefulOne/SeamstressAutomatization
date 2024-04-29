package com.example.seamstressautomatization.data.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.seamstressautomatization.data.entities.Operation

@Dao
interface OperationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(operation: Operation)

    @Update
    fun update(operation: Operation)

    @Query("DELETE FROM operations WHERE operation_name = :name")
    fun delete(name: String)

    @Query("SELECT * from operations WHERE operation_name = :name")
    fun getItem(name: String): List<Operation>

    @Query("SELECT * from operations")
    fun getAllItems(): LiveData<List<Operation>>

    @Query("DELETE FROM operations")
    fun deleteAll()
}