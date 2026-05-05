package com.example.unchain.data.repository

import com.example.unchain.data.db.PersonalizationDao
import com.example.unchain.data.models.dbModels.PersonalityDbModel
import com.example.unchain.data.models.mapper.AddictionPersonalityPurchaseMapper
import com.example.unchain.data.models.mapper.AddictionWithPersonalityMapper
import com.example.unchain.data.models.mapper.PersonalityMapper
import com.example.unchain.data.models.mapper.ThemeMapper
import com.example.unchain.domain.models.personalization.AddictionPersonalityPurchase
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.models.personalization.Theme
import com.example.unchain.domain.repositories.PersonalizationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PersonalizationRepositoryImpl @Inject constructor(
    private val personalityMapper: PersonalityMapper,
    private val themeMapper: ThemeMapper,
    private val addictionWithPersonalityMapper: AddictionWithPersonalityMapper,
    private val personalizationDao: PersonalizationDao,
    private val addictionPersonalityPurchaseMapper: AddictionPersonalityPurchaseMapper
) : PersonalizationRepository{
    override fun getAllPersonalities(): Flow<List<Personality>>{
        return personalizationDao.getAllPersonalities().map {
            personalityMapper.dbModelToEntityList(it)
        }
    }

    override fun getPersonalityIdByAddictionId(addictionId: Int): Flow<Int?> {
        return personalizationDao.getPersonalityIdByAddictionId(addictionId)
    }

    override suspend fun getThemeIdByPersonalityId(personalityId: Int): Int? {
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

    override suspend fun getPersonalityById(personalityId: Int): Personality {
        return personalityMapper.dbModelToEntity(personalizationDao.getPersonalityById(personalityId))
    }

    override suspend fun updatePersonality(personality: Personality) {
        personalizationDao.updatePersonality(personalityMapper.entityToDbModel(personality))
    }

    override fun getPurchasedIds(id: Int): Flow<List<Int>> {
        return personalizationDao.getPurchasedIds(id)
    }

    override suspend fun insertPurchase(addictionPersonalityPurchase: AddictionPersonalityPurchase) {
        personalizationDao.insertPurchase(addictionPersonalityPurchaseMapper.entityToDbModel(addictionPersonalityPurchase))
    }
}