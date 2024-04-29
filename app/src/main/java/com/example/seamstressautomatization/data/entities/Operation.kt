package com.example.seamstressautomatization.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "operations")
data class Operation(
    @PrimaryKey (autoGenerate = true)
    var operation_id: Int,
    var operation_name: String,
    var duration_sec: Int
)
