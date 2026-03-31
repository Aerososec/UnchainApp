package com.example.unchain.data.repository

import com.example.unchain.data.db.AddictionInWidgetDao
import com.example.unchain.data.models.Mapper
import com.example.unchain.domain.models.Addiction
import com.example.unchain.domain.models.AddictionInWidget
import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.domain.repositories.AddictionInWidgetRepository
import javax.inject.Inject

class AddictionInWidgetRepositoryImpl @Inject constructor(
    private val mapper: Mapper,
    private val addictionInWidgetDao: AddictionInWidgetDao
) : AddictionInWidgetRepository {
    override suspend fun setAddictionInWidget(addiction: AddictionInWidget) {
        addictionInWidgetDao.insertAddictionInWidget(mapper.entityToDbModelAddictionInWidget(addiction))
    }

    override suspend fun getAddictions(): List<Addiction> {
       return mapper.dbModelToEntityToAddictionList(addictionInWidgetDao.getAddictionsForWidgetConfig())
    }

    override suspend fun getAddictionIdByWidgetId(widgetId: Int): Int? {
        return addictionInWidgetDao.getAddictionIdByWidgetId(widgetId)
    }

    override suspend fun getWidgetIdByAddictionId(addictionId: Int): Int? {
        return addictionInWidgetDao.getWidgetIdByAddictionId(addictionId)
    }

    override suspend fun getAddictionInfo(addictionId: Int): AddictionWithProgress {
        return mapper.dbModelToEntityAddictionWithProgress(addictionInWidgetDao.getAddictionInfo(addictionId))
    }
}