package com.example.unchain.domain.usecases

import com.example.unchain.data.db.AddictionDao
import com.example.unchain.data.defaultAddictions.DefaultAddictions
import com.example.unchain.data.models.mapper.Mapper
import javax.inject.Inject

class InitDbUseCase @Inject constructor(private val addictionDao: AddictionDao, private val mapper: Mapper) {
    suspend fun execute(){
        addictionDao.insertAll(mapper.entityToDbModelAddictionList(DefaultAddictions.list))
    }
}