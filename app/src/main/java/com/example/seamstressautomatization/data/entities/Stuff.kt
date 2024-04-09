package com.example.seamstressautomatization.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stuff")
data class Stuff(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stuff_name: String,
    val status: String,
    val occupation: String
)
