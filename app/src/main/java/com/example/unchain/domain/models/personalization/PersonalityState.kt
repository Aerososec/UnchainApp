package com.example.unchain.domain.models.personalization

enum class PersonalityState(val state : String) {
    LOCKED("LOCKED"),
    UNLOCKED_SELECTED("UNLOCKED_SELECTED"),
    UNLOCKED_NOT_SELECTED("UNLOCKED_NOT_SELECTED")
}