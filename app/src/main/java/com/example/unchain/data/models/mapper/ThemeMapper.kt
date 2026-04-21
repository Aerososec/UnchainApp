package com.example.unchain.data.models.mapper

import com.example.unchain.data.models.dbModels.ThemeDbModel
import com.example.unchain.domain.models.personalization.Theme
import javax.inject.Inject

class ThemeMapper @Inject constructor(){
    fun dbModelToEntity(theme: ThemeDbModel) : Theme{
        return Theme(
            theme.id,
            theme.name,
            theme.colors,
            theme.background,
            theme.styleType
        )
    }

    fun entityToDbModel(theme: Theme) : ThemeDbModel{
        return ThemeDbModel(
            theme.id,
            theme.name,
            theme.colors,
            theme.background,
            theme.styleType
        )
    }
}