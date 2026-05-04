package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.repositories.PersonalizationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPersonalitiesUseCase @Inject constructor(private val repository: PersonalizationRepository) {
    operator fun invoke() : Flow<List<Personality>>{
        return repository.getAllPersonalities()
    }
}