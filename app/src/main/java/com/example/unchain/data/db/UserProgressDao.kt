package com.example.unchain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unchain.data.models.UserProgressDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {

    @Query("SELECT * FROM user_progress WHERE addictionId =:id LIMIT 1")
    fun getUserProgress(id : Int) : Flow<UserProgressDbModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(userProgress : UserProgressDbModel)

}