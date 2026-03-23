package com.example.unchain.domain.models

data class Addiction(
    val id: Int,
    val name: String,
    val isActive: Boolean = false,
)
