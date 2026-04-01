package com.example.unchain.data.repository

import com.example.unchain.data.db.MessageDao
import com.example.unchain.data.models.MessageMapper
import com.example.unchain.domain.models.gemini.ContentRequest
import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.models.gemini.Message
import com.example.unchain.domain.models.gemini.MessageRole
import com.example.unchain.domain.models.gemini.PartRequest
import com.example.unchain.domain.repositories.GeminiRepository
import com.example.unchain.domain.repositories.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val messageMapper: MessageMapper,
    private val geminiRepository: GeminiRepository
) : MessageRepository {
    override suspend fun sendMessage(message: Message): GeminiResponse {
        insertMessage(message)
        return try {
            val request = makeRequest(message)
            val geminiAnswer = geminiRepository.getGeminiResponse(request)
            geminiAnswer.candidates?.get(0)?.content?.parts?.get(0)?.text.let {
                val answerMessage = makeMessage(it.toString(), message.addictionId)
                insertMessage(answerMessage)
            }
            geminiAnswer
        }
        catch (e : Exception){
            throw e
        }
    }

    override fun getChatForAddiction(addictionId: Int): Flow<List<Message>> {
        return messageDao.getMessageByAddictionId(addictionId).map { messageMapper.dbModelToEntityList(it) }
    }

    private suspend fun insertMessage(message: Message){
        messageDao.insertMessage(messageMapper.entityToDbModel(message))
    }

    private fun makeRequest(message: Message) : GeminiRequest{
        val partRequest = PartRequest(message.text)
        val contentRequest = ContentRequest(listOf(partRequest))
        val geminiRequest = GeminiRequest(listOf(contentRequest))
        return geminiRequest
    }

    private fun makeMessage(text : String, addictionId: Int) : Message{
        val message = Message(id = 0, text = text, role = MessageRole.GEMINI, addictionId = addictionId)
        return message
    }
}