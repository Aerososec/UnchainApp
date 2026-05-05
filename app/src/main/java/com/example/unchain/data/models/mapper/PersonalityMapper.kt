package com.example.unchain.data.models.mapper

import com.example.unchain.data.models.dbModels.PersonalityDbModel
import com.example.unchain.domain.models.personalization.Personality
import javax.inject.Inject

class PersonalityMapper @Inject constructor() {
    fun entityToDbModel(personality: Personality) : PersonalityDbModel{
        return PersonalityDbModel(
            personality.id,
            personality.name,
            personality.description,
            personality.price,
            personality.promptModifier,
            personality.themeId,
            state = personality.state
        )
    }

    fun dbModelToEntity(personality: PersonalityDbModel) : Personality{
        return Personality(
            personality.id,
            personality.name,
            personality.description,
            personality.price,
            personality.promptModifier,
            personality.themeId,
            state = personality.state
        )
    }

    fun entityToDbModelList(personalityList : List<Personality>) : List<PersonalityDbModel>{
        return personalityList.map { entityToDbModel(it) }
    }

    fun dbModelToEntityList(personalityList : List<PersonalityDbModel>) : List<Personality>{
        return personalityList.map { dbModelToEntity(it) }
    }
}