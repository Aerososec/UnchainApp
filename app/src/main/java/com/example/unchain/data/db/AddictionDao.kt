package com.example.unchain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unchain.data.models.AddictionDbModel
import com.example.unchain.data.models.AddictionWithProgressDbModel
import com.example.unchain.domain.models.Addiction
import kotlinx.coroutines.flow.Flow


@Dao
interface AddictionDao {
    @Query(
        """
SELECT 
    a.id AS id,
    a.name AS name,
    a.isActive AS isActive,
    p.currentStreak AS currentStreak
FROM addiction a
LEFT JOIN user_progress p
ON a.id = p.addictionId
"""
    )
    fun getAllAddictions(): Flow<List<AddictionWithProgressDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list : List<AddictionDbModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAddiction(addiction: AddictionDbModel)

    @Query("SELECT COUNT(*) FROM addiction")
    suspend fun getAllCount() : Int

    @Query("SELECT * FROM addiction WHERE id =:addictionId LIMIT 1")
    suspend fun getAddictionById(addictionId : Int) : AddictionDbModel

}