package com.example.unchain.domain.repositories

import com.example.unchain.domain.models.personalization.AddictionPersonalityPurchase
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.models.personalization.Theme
import kotlinx.coroutines.flow.Flow

interface PersonalizationRepository {
    fun getAllPersonalities() : Flow<List<Personality>>
    fun getPersonalityIdByAddictionId(addictionId : Int) : Flow<Int?>
    suspend fun getThemeIdByPersonalityId(personalityId : Int) : Int?
    suspend fun getTheme(themeId : Int) : Theme
    suspend fun insertAddictionWithPersonality(addictionWithPersonality: AddictionWithPersonality)
    suspend fun getPersonalityById(personalityId : Int) : Personality
    suspend fun updatePersonality(personality: Personality)
    fun getPurchasedIds(id: Int): Flow<List<Int>>
    suspend fun insertPurchase(addictionPersonalityPurchase : AddictionPersonalityPurchase)
}