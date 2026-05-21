package com.example.unchain.data.repository

import android.util.Log
import com.example.unchain.data.db.MessageDao
import com.example.unchain.data.models.mapper.MessageMapper
import com.example.unchain.domain.models.gemini.ContentRequest
import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.models.gemini.Message
import com.example.unchain.domain.models.gemini.MessageRole
import com.example.unchain.domain.models.gemini.PartRequest
import com.example.unchain.domain.repositories.GeminiRepository
import com.example.unchain.domain.repositories.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val messageMapper: MessageMapper
) : MessageRepository {
    override fun getChatForAddiction(addictionId: Int): Flow<List<Message>> {
        return messageDao.getMessageByAddictionId(addictionId).map { messageMapper.dbModelToEntityList(it) }
    }

    override suspend fun insertMessage(message: Message){
        messageDao.insertMessage(messageMapper.entityToDbModel(message))
    }

}