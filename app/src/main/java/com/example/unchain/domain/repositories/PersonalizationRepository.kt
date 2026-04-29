package com.example.unchain.domain.repositories

import com.example.unchain.data.models.dbModels.PersonalityDbModel
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.models.personalization.Theme

interface PersonalizationRepository {
    suspend fun getAllPersonalities() : List<Personality>
    suspend fun getPersonalityIdByAddictionId(addictionId : Int) : Int?
    suspend fun getThemeIdByPersonalityId(personalityId : Int) : Int?
    suspend fun getTheme(themeId : Int) : Theme
    suspend fun insertAddictionWithPersonality(addictionWithPersonality: AddictionWithPersonality)
    suspend fun getPersonalityById(personalityId : Int) : Personality
    suspend fun updatePersonality(personality: Personality)
}