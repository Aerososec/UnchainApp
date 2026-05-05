package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.models.personalization.AddictionPersonalityPurchase
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.repositories.PersonalizationRepository
import com.example.unchain.domain.repositories.UserRepository
import kotlinx.coroutines.flow.firstOrNull
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
        return userRepository.getUserProgress(addictionId).firstOrNull() ?: throw Exception()
    }

    private suspend fun getPersonality(personalityId : Int) : Personality{
        return personalityRepository.getPersonalityById(personalityId)
    }

    private suspend fun attemptToBuy(userProgress: UserProgress, personality: Personality, awp: AddictionWithPersonality){
        val userCurrency = userProgress.currency
        val personalityPrice = personality.price
        if (checkPurchases(awp.addictionId, awp.personalityId)){
            updateAddictionPersonality(awp)
        }
        else{
            if (userCurrency < personalityPrice) return
            val currentCurrency = userCurrency - personalityPrice
            updateUserProgress(userProgress, currentCurrency)
            updatePurchases(awp.addictionId, awp.personalityId)
        }
    }

    private suspend fun checkPurchases(addictionId: Int, personalityId : Int) : Boolean{
        val purchases = personalityRepository.getPurchasedIds(addictionId).firstOrNull() ?: emptyList()
        return personalityId in purchases
    }

    private suspend fun updateAddictionPersonality(awp : AddictionWithPersonality){
        personalityRepository.insertAddictionWithPersonality(awp)
    }

    private suspend fun updatePurchases(addictionId: Int, personalityId: Int){
        val newPurchase = AddictionPersonalityPurchase(addictionId, personalityId)
        personalityRepository.insertPurchase(newPurchase)
    }

    private suspend fun updateUserProgress(userProgress : UserProgress, newCurrency : Int){
        val newUserProgress = userProgress.copy(currency = newCurrency)
        userRepository.saveUserProgress(newUserProgress)
    }
}