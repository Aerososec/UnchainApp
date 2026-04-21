package com.example.unchain.data.models.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "themes")
data class ThemeDbModel(
    @PrimaryKey
    val id : Int,
    val name : String,
    val colors : Map<String, Int>,
    val background : Int,
    val styleType : Int
)
