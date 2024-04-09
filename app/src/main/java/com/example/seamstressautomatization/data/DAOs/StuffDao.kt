package com.example.seamstressautomatization.data.DAOs
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
    suspend fun insert(stuff: Stuff)

    @Update
    suspend fun update(stuff: Stuff)

    @Delete
    suspend fun delete(stuff: Stuff)

    @Query("SELECT * from stuff WHERE id = :id")
    fun getItem(id: Int): Flow<Stuff>

    @Query("SELECT * from stuff ORDER BY stuff_name ASC")
    fun getAllItems(): Flow<List<Stuff>>
}