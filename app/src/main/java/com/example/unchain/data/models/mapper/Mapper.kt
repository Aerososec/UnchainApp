package com.example.unchain.data.models.mapper

import com.example.unchain.data.models.dbModels.AddictionDbModel
import com.example.unchain.data.models.dbModels.AddictionInWidgetDbModel
import com.example.unchain.data.models.dbModels.AddictionWithProgressDbModel
import com.example.unchain.data.models.dbModels.UserProgressDbModel
import com.example.unchain.domain.models.Addiction
import com.example.unchain.domain.models.AddictionInWidget
import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.domain.models.UserProgress
import javax.inject.Inject

class Mapper @Inject constructor() {

    fun entityToDbModelAddiction(addiction: Addiction) : AddictionDbModel {
        return AddictionDbModel(
            addiction.id,
            addiction.name,
            addiction.isActive
        )
    }

    fun dbModelToEntityAddictionWithProgress(addictionDb: AddictionWithProgressDbModel) : AddictionWithProgress {
        return AddictionWithProgress(
            addictionDb.id,
            addictionDb.name,
            addictionDb.isActive,
            addictionDb.currentStreak
        )
    }

    fun dbModelToEntityAddiction(addiction: AddictionDbModel) : Addiction {
        return Addiction(
            addiction.id,
            addiction.name,
            addiction.isActive
        )
    }

    fun dbModelToEntityToAddictionList(addiction: List<AddictionDbModel>) : List<Addiction>{
        return addiction.map { dbModelToEntityAddiction(it) }
    }


    fun entityToDbModelAddictionList(addictions: List<Addiction>) = addictions.map {
        entityToDbModelAddiction(it)
    }

    fun dbModelToEntityAddictionList(addictions: List<AddictionWithProgressDbModel>) = addictions.map {
        dbModelToEntityAddictionWithProgress(it)
    }

    fun entityToDbModelUserProgress(userProgress: UserProgress) : UserProgressDbModel {
        return UserProgressDbModel(
            userProgress.addictionId,
            userProgress.startDate,
            userProgress.currentStreak,
            userProgress.bestStreak,
            userProgress.currency,
            userProgress.lastCheckDate,
            userProgress.dayResult,
            userProgress.hour,
            userProgress.minute
        )
    }

    fun dbModelToEntityUserProgress(userProgressDb: UserProgressDbModel) : UserProgress {
        return UserProgress(
            userProgressDb.addictionId,
            userProgressDb.startDate,
            userProgressDb.currentStreak,
            userProgressDb.bestStreak,
            userProgressDb.currency,
            userProgressDb.lastCheckDate,
            userProgressDb.dayResult,
            userProgressDb.hour,
            userProgressDb.minute
        )
    }

    fun dbModelToEntityAddictionInWidget(addictionInWidgetDbModel: AddictionInWidgetDbModel) : AddictionInWidget {
        return AddictionInWidget(
            addictionInWidgetDbModel.widgetId,
            addictionInWidgetDbModel.addictionId
        )
    }

    fun entityToDbModelAddictionInWidget(addictionInWidget: AddictionInWidget) : AddictionInWidgetDbModel {
        return AddictionInWidgetDbModel(
            addictionInWidget.widgetId,
            addictionInWidget.addictionId
        )
    }
}