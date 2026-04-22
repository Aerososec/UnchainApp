package com.example.unchain.data.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.unchain.data.models.dbModels.AddictionWithPersonalityDbModel
import com.example.unchain.data.models.dbModels.PersonalityDbModel
import com.example.unchain.data.models.dbModels.ThemeDbModel

interface PersonalizationDao {
    @Query("SELECT * FROM personality")
    suspend fun getAllPersonalities() : List<PersonalityDbModel>

    @Query("SELECT * FROM personality WHERE id =:personalityId LIMIT 1")
    suspend fun getPersonalityById(personalityId : Int) : PersonalityDbModel

    @Query("SELECT personalityId FROM addiction_with_personality WHERE addictionId =:addictionId LIMIT 1")
    suspend fun getPersonalityIdByAddictionId(addictionId : Int) : Int

    @Query("SELECT themeId FROM personality WHERE id =:personalityId LIMIT 1")
    suspend fun getThemeIdByPersonalityId(personalityId : Int) : Int

    @Query("SELECT * FROM themes WHERE id =:themeId LIMIT 1")
    suspend fun getTheme(themeId : Int) : ThemeDbModel

    @Insert(onConflict = REPLACE)
    suspend fun insertAddictionWithPersonality(addictionWithPersonalityDbModel: AddictionWithPersonalityDbModel)

    @Insert(onConflict = REPLACE)
    suspend fun updatePersonality(personalityDbModel: PersonalityDbModel)
}