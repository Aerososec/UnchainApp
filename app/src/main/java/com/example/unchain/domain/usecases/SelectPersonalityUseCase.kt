package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.repositories.PersonalizationRepository
import com.example.unchain.domain.repositories.UserRepository
import kotlinx.coroutines.flow.lastOrNull
import javax.inject.Inject

class SelectPersonalityUseCase @Inject constructor(
    private val personalityRepository: PersonalizationRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(addictionWithPersonality: AddictionWithPersonality){
        val userProgress = getAddiction(addictionWithPersonality.addictionId)
        val personality = getPersonality(addictionWithPersonality.personalityId)
        attemptToBuy(userProgress, personality, addictionWithPersonality)
    }

    private suspend fun getAddiction(addictionId : Int) : UserProgress{
        return userRepository.getUserProgress(addictionId).lastOrNull() ?: throw Exception()
    }

    private suspend fun getPersonality(personalityId : Int) : Personality{
        return personalityRepository.getPersonalityById(personalityId)
    }

    private suspend fun attemptToBuy(userProgress: UserProgress, personality: Personality, awp: AddictionWithPersonality){
        val userCurrency = userProgress.currency
        val personalityPrice = personality.price
        if (personality.isUnlocked){
            personalityRepository.insertAddictionWithPersonality(awp)
            return
        }
        if (userCurrency < personalityPrice) return
        updateUserProgress(userProgress, userCurrency - personalityPrice)
        personalityRepository.insertAddictionWithPersonality(awp)
        updatePersonality(personality)
    }

    private suspend fun updatePersonality(personality: Personality){
        val updatePersonality = personality.copy(isUnlocked = true)
        personalityRepository.updatePersonality(personality)
    }

    private suspend fun updateUserProgress(userProgress : UserProgress, newCurrency : Int){
        val newUserProgress = userProgress.copy(currency = newCurrency)
        userRepository.saveUserProgress(newUserProgress)
    }
}