package com.example.seamstressautomatization.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stuff")
data class Stuff(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var stuff_name: String,
    var salary: Int,
)
