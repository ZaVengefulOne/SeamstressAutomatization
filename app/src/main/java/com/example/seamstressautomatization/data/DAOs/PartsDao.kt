package com.example.seamstressautomatization.data.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.seamstressautomatization.data.entities.Part

@Dao
interface PartsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(part: Part)

    @Update
    fun update(part: Part)

    @Query("DELETE FROM parts WHERE part_name = :name")
    fun delete(name: String)

    @Query("SELECT * from parts WHERE part_name = :name")
    fun getItem(name: String): List<Part>

    @Query("SELECT * from parts")
    fun getAllItems(): LiveData<List<Part>>

    @Query("DELETE FROM parts")
    fun deleteAll()
}