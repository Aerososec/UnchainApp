package com.example.unchain.domain.models.personalization

data class Personality(
    val id : Int,
    val name : String,
    val description : String,
    val price : Int,
    val promptModifier : String,
    val themeId : Int,
    val isUnlocked : Boolean = false
)
