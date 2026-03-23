package com.example.unchain.domain.models

data class UserProgress(
    val addictionId : Int,
    val startDate : Long,
    val currentStreak : Int = 0,
    val bestStreak : Int = 0,
    val currency : Int = 0,
    val lastCheckDate : Long = 0,
    val dayResult : Boolean = false,
    val hour : Int? = null,
    val minute : Int? = null
)
