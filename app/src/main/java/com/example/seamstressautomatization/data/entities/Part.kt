package com.example.seamstressautomatization.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "parts")
data class Part(
    @PrimaryKey (autoGenerate = true)
    var part_id: Int,
    var part_name: String,
    var part_base_payment: Float
)
