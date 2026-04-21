package com.example.unchain.data.models.dbModels

data class AddictionWithProgressDbModel(
    val id: Int,
    val name: String,
    val isActive : Boolean,
    val currentStreak : Int?
)
