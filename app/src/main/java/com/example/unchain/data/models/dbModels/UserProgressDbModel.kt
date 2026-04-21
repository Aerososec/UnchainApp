package com.example.unchain.data.models.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressDbModel(
    @PrimaryKey
    val addictionId : Int,
    val startDate : Long,
    val currentStreak : Int,
    val bestStreak : Int,
    val currency : Int,
    val lastCheckDate : Long,
    val dayResult : Boolean,
    val hour : Int? = null,
    val minute : Int? = null
)
