package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.repositories.PersonalizationRepository
import javax.inject.Inject

class GetAllPersonalitiesUseCase @Inject constructor(private val repository: PersonalizationRepository) {
    suspend operator fun invoke() : List<Personality>{
        return repository.getAllPersonalities()
    }
}