package com.example.seamstressautomatization.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clothes")
data class Cloth(
    @PrimaryKey(autoGenerate = true)
    var cloth_id: Int,

    var cloth_name : String,

)