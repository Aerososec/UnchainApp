package com.example.unchain.data.models.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personality")
data class PersonalityDbModel(
    @PrimaryKey()
    val id : Int,
    val name : String,
    val description : String,
    val price : Int,
    val promptModifier : String,
    val themeId : Int,
    val state : String
)