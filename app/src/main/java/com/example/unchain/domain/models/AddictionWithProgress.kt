package com.example.unchain.domain.models

data class AddictionWithProgress(
    val id: Int,
    val name: String,
    val isActive : Boolean = false,
    val currentStreak : Int? = 0
)