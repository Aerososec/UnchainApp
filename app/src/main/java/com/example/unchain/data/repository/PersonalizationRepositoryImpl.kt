package com.example.unchain.data.repository

import com.example.unchain.data.db.PersonalizationDao
import com.example.unchain.data.models.mapper.AddictionWithPersonalityMapper
import com.example.unchain.data.models.mapper.PersonalityMapper
import com.example.unchain.data.models.mapper.ThemeMapper
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.models.personalization.Theme
import com.example.unchain.domain.repositories.PersonalizationRepository
import javax.inject.Inject

class PersonalizationRepositoryImpl @Inject constructor(
    private val personalityMapper: PersonalityMapper,
    private val themeMapper: ThemeMapper,
    private val addictionWithPersonalityMapper: AddictionWithPersonalityMapper,
    private val personalizationDao: PersonalizationDao
) : PersonalizationRepository{
    override suspend fun getAllPersonalities(): List<Personality> {
        return personalityMapper.dbModelToEntityList(personalizationDao.getAllPersonalities())
    }

    override suspend fun getPersonalityIdByAddictionId(addictionId: Int): Int {
        return personalizationDao.getPersonalityIdByAddictionId(addictionId)
    }

    override suspend fun getThemeIdByPersonalityId(personalityId: Int): Int {
        return personalizationDao.getThemeIdByPersonalityId(personalityId)
    }

    override suspend fun getTheme(themeId: Int): Theme {
        return themeMapper.dbModelToEntity(personalizationDao.getTheme(themeId))
    }

    override suspend fun insertAddictionWithPersonality(addictionWithPersonality: AddictionWithPersonality) {
        personalizationDao.insertAddictionWithPersonality(
            addictionWithPersonalityMapper.entityToDbModel(addictionWithPersonality)
        )
    }
}