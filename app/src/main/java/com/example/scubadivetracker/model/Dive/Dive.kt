package com.example.scubadivetracker.model.Dive

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dive_table")
data class Dive(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val location: String,
    val depth: Double,
    val duration: Int,
    val date: String,
    val notes: String
)
