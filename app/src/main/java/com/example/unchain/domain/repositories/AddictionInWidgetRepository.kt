package com.example.unchain.domain.repositories

import com.example.unchain.domain.models.Addiction
import com.example.unchain.domain.models.AddictionInWidget
import com.example.unchain.domain.models.AddictionWithProgress

interface AddictionInWidgetRepository {
    suspend fun setAddictionInWidget(addiction: AddictionInWidget)
    suspend fun getAddictions() : List<Addiction>
    suspend fun getAddictionIdByWidgetId(widgetId : Int) : Int?
    suspend fun getWidgetIdByAddictionId(addictionId : Int) : Int?
    suspend fun getAddictionInfo(addictionId : Int) : AddictionWithProgress
}