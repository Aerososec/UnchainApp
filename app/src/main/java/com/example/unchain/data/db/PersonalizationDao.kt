package com.example.unchain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.unchain.data.models.dbModels.AddictionPersonalityPurchaseDbModel
import com.example.unchain.data.models.dbModels.AddictionWithPersonalityDbModel
import com.example.unchain.data.models.dbModels.PersonalityDbModel
import com.example.unchain.data.models.dbModels.ThemeDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalizationDao {
    @Query("SELECT * FROM personality")
    fun getAllPersonalities() : Flow<List<PersonalityDbModel>>

    @Query("SELECT * FROM personality WHERE id =:personalityId LIMIT 1")
    suspend fun getPersonalityById(personalityId : Int) : PersonalityDbModel

    @Query("SELECT personalityId FROM addiction_with_personality WHERE addictionId =:addictionId LIMIT 1")
    fun getPersonalityIdByAddictionId(addictionId : Int) : Flow<Int?>

    @Query("SELECT themeId FROM personality WHERE id =:personalityId LIMIT 1")
    suspend fun getThemeIdByPersonalityId(personalityId : Int) : Int?

    @Query("SELECT * FROM themes WHERE id =:themeId LIMIT 1")
    suspend fun getTheme(themeId : Int) : ThemeDbModel

    @Insert(onConflict = REPLACE)
    suspend fun insertAddictionWithPersonality(addictionWithPersonalityDbModel: AddictionWithPersonalityDbModel)

    @Insert(onConflict = REPLACE)
    suspend fun updatePersonality(personalityDbModel: PersonalityDbModel)

    @Query("SELECT personalityId FROM addiction_personality_purchase WHERE addictionId = :id")
    fun getPurchasedIds(id: Int): Flow<List<Int>>

    @Insert(onConflict = REPLACE)
    suspend fun insertPurchase(addictionPersonalityPurchase : AddictionPersonalityPurchaseDbModel)
}