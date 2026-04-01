package com.example.unchain.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.unchain.data.models.MessageDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(messageDbModel: MessageDbModel)

    @Query("SELECT * FROM GEMINI_CHAT WHERE addictionId=:addictionId ORDER BY id ASC")
    fun getMessageByAddictionId(addictionId : Int) : Flow<List<MessageDbModel>>
}