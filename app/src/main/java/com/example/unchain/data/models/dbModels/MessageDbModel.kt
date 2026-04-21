package com.example.unchain.data.models.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gemini_chat")
data class MessageDbModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val text : String,
    val role : String,
    val addictionId : Int
)