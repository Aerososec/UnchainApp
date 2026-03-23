package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.provider.TimeProvider
import com.example.unchain.domain.repositories.UserRepository
import javax.inject.Inject

class StartAddictionUseCase @Inject constructor(private val userRepository: UserRepository, private val timeProvider: TimeProvider) {
    suspend fun execute(addictionId : Int){
        val userProgress = UserProgress(addictionId = addictionId, startDate = timeProvider.now())
        val oldAddiction = userRepository.getAddictionById(addictionId)
        val addictionUpdate = oldAddiction.copy(isActive = true)
        userRepository.insertAddiction(addictionUpdate)
        userRepository.startAddiction(userProgress)
    }
}