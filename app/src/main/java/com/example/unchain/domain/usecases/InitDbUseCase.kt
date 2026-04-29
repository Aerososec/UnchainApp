package com.example.unchain.domain.usecases

import com.example.unchain.data.db.AddictionDao
import com.example.unchain.data.defaultAddictions.DefaultAddictions
import com.example.unchain.data.models.mapper.Mapper
import com.example.unchain.data.repository.PersonalizationRepositoryImpl
import javax.inject.Inject

class InitDbUseCase @Inject constructor(private val addictionDao: AddictionDao, private val mapper: Mapper, private val personalizationRepositoryImpl: PersonalizationRepositoryImpl) {
    suspend fun execute(){
        addictionDao.insertAll(mapper.entityToDbModelAddictionList(DefaultAddictions.list))
        for (i in DefaultAddictions.listPersonality){
            personalizationRepositoryImpl.updatePersonality(i)
        }
    }
}