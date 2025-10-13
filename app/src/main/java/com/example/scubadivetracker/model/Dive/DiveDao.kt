package com.example.scubadivetracker.model.Dive

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Data Access Object (DAO) for the Dive entity.
 * Defines methods for interacting with the dive_table in the Room database.
 */
@Dao
interface DiveDao {
    @Insert
    suspend fun insert(dive: Dive)

    @Delete
    suspend fun delete(dive: Dive)

    @Update
    suspend fun update(dive: Dive)
    /**
     * Retrieves all dives from the database, ordered by date in descending order.
     *
     * @return A LiveData list of Dive objects, automatically updated when data changes.
     */
    @Query("SELECT * FROM dive_table ORDER BY date DESC")
    fun getAllDives(): LiveData<List<Dive>>
}
