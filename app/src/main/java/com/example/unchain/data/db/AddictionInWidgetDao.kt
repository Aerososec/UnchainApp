package com.example.unchain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unchain.data.models.dbModels.AddictionDbModel
import com.example.unchain.data.models.dbModels.AddictionInWidgetDbModel
import com.example.unchain.data.models.dbModels.AddictionWithProgressDbModel

@Dao
interface AddictionInWidgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertAddictionInWidget(addictionInWidgetDbModel: AddictionInWidgetDbModel)

    @Query("SELECT * FROM addiction")
    suspend fun getAddictionsForWidgetConfig() : List<AddictionDbModel>

    @Query("SELECT addictionId FROM addiction_in_widget WHERE widgetId =:widgetId LIMIT 1")
    suspend fun getAddictionIdByWidgetId(widgetId : Int) : Int?

    @Query("SELECT widgetId FROM addiction_in_widget WHERE addictionId =:addictionId")
    suspend fun getWidgetIdByAddictionId(addictionId : Int) : Int?

    @Query("SELECT a.id AS id,\n" +
            "    a.name AS name,\n" +
            "    a.isActive AS isActive,\n" +
            "    p.currentStreak AS currentStreak\n" +
            "FROM addiction a\n" +
            "LEFT JOIN user_progress p\n" +
            "ON a.id = p.addictionId " +
            "WHERE a.id =:addictionId")
    suspend fun getAddictionInfo(addictionId : Int) : AddictionWithProgressDbModel
}