package com.example.unchain.data.models.mapper

import com.example.unchain.data.models.dbModels.AddictionWithPersonalityDbModel
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import javax.inject.Inject

class AddictionWithPersonalityMapper @Inject constructor() {
    fun entityToDbModel(awp : AddictionWithPersonality) : AddictionWithPersonalityDbModel{
        return AddictionWithPersonalityDbModel(
            awp.addictionId,
            awp.personalityId
        )
    }

    fun dbModelToDbModel(awp : AddictionWithPersonality) : AddictionWithPersonalityDbModel{
        return AddictionWithPersonalityDbModel(
            awp.addictionId,
            awp.personalityId
        )
    }
}