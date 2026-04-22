package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.personalization.Theme
import com.example.unchain.domain.repositories.PersonalizationRepository
import javax.inject.Inject

class GetThemeForAddiction @Inject constructor(
    private val personalityRepository: PersonalizationRepository
) {
    suspend operator fun invoke(addictionId : Int) : Theme{
        val personalityId = personalityRepository.getPersonalityIdByAddictionId(addictionId)
        val themeId = personalityRepository.getThemeIdByPersonalityId(personalityId)
        return personalityRepository.getTheme(themeId)
    }
}