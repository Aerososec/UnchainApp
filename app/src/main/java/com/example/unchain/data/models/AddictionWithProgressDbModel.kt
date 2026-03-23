package com.example.unchain.data.models

import androidx.room.Entity

data class AddictionWithProgressDbModel(
    val id: Int,
    val name: String,
    val isActive : Boolean,
    val currentStreak : Int?
)
