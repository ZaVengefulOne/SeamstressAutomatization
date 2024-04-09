package com.example.seamstressautomatization.data.repositories
import com.example.seamstressautomatization.data.entities.Stuff
import kotlinx.coroutines.flow.Flow

interface StuffRepository {
    /**
     * Retrieve all the stuff members from the given data source.
     */
    fun getAllStuffStream(): Flow<List<Stuff>>

    /**
     * Retrieve a stuff member from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Stuff?>

    /**
     * Insert stuff member in the data source
     */
    suspend fun insertStuff(stuff: Stuff)

    /**
     * Delete stuff member from the data source
     */
    suspend fun deleteStuff(stuff: Stuff)

    /**
     * Update stuff in the data source
     */
    suspend fun updateStuff(stuff: Stuff)
}
