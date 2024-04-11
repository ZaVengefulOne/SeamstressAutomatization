package com.example.seamstressautomatization.data.DAOs

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.seamstressautomatization.data.entities.Cloth

@Dao
interface ClothesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cloth: Cloth)

    @Update
    fun update(cloth: Cloth)

    @Query("DELETE FROM clothes WHERE cloth_name = :name")
    fun delete(name: String)

    @Query("SELECT * from clothes WHERE cloth_name = :name")
    fun getItem(name: String): List<Cloth>

    @Query("SELECT * from clothes")
    fun getAllItems(): LiveData<List<Cloth>>

    @Query("DELETE FROM clothes")
    fun deleteAll()
}