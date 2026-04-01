package com.example.unchain.domain.models.gemini

data class Message(
    val id : Int,
    val text : String,
    val role : MessageRole,
    val addictionId : Int
)
