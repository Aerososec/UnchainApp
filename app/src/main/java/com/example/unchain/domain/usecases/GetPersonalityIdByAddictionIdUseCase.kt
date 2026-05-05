package com.example.unchain.domain.usecases

import com.example.unchain.domain.repositories.PersonalizationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPersonalityIdByAddictionIdUseCase @Inject constructor(
    private val personalityRepository: PersonalizationRepository
) {
    operator fun invoke(addictionId : Int): Flow<Int?>{
        return personalityRepository.getPersonalityIdByAddictionId(addictionId)
    }
}