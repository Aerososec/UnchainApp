package com.example.unchain.domain.models.gemini

data class Message(
    val id : Int,
    val text : String,
    val role : String,
    val addictionId : Int
)
