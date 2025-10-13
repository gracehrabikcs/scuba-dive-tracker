package com.example.scubadivetracker.model.Dive

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.lifecycle.LiveData
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface DiveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dive: Dive)

    @Query("SELECT * FROM dive_table ORDER BY date DESC")
    fun getAllDives(): LiveData<List<Dive>>

    @Delete
    suspend fun delete(dive: Dive)

    @Update
    suspend fun update(dive: Dive)
}