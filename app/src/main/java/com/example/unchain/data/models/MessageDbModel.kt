package com.example.unchain.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.unchain.domain.models.gemini.MessageRole

@Entity(tableName = "gemini_chat")
data class MessageDbModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val text : String,
    val role : String,
    val addictionId : Int
)