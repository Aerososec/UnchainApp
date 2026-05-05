package com.example.unchain.data.models.mapper

import com.example.unchain.data.models.dbModels.AddictionPersonalityPurchaseDbModel
import com.example.unchain.domain.models.personalization.AddictionPersonalityPurchase
import javax.inject.Inject

class AddictionPersonalityPurchaseMapper @Inject constructor() {
    fun entityToDbModel(app : AddictionPersonalityPurchase) : AddictionPersonalityPurchaseDbModel{
        return AddictionPersonalityPurchaseDbModel(
            app.addictionId,
            app.personalityId
        )
    }

    fun dbModelToEntity(app : AddictionPersonalityPurchaseDbModel) : AddictionPersonalityPurchase{
        return AddictionPersonalityPurchase(
            app.addictionId,
            app.personalityId
        )
    }
}