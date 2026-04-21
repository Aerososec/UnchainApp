package com.example.unchain.domain.models.personalization

data class Theme(
    val id : Int,
    val name : String,
    val colors : Map<String, Int>,
    val background : Int,
    val styleType : Int
)
