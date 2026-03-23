package com.example.unchain.domain.provider

interface TimeProvider {
    fun now() : Long
}