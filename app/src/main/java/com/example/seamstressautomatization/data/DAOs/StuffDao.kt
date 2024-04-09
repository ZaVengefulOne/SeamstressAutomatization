package com.example.seamstressautomatization.data.DAOs
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.seamstressautomatization.data.entities.Stuff
import kotlinx.coroutines.flow.Flow

@Dao
interface StuffDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(stuff: Stuff)

    @Update
    fun update(stuff: Stuff)

    @Query("DELETE FROM stuff WHERE stuff_name = :name")
    fun delete(name: String)

    @Query("SELECT * from stuff WHERE stuff_name = :name")
    fun getItem(name: String): List<Stuff>

    @Query("SELECT * from stuff")
    fun getAllItems(): LiveData<List<Stuff>>
}