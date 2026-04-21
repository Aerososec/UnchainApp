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
    private val messageMapper: MessageMapper,
    private val geminiRepository: GeminiRepository
) : MessageRepository {
    override suspend fun sendMessage(message: Message): List<GeminiResponse> {
        insertMessage(message)
        return try {
            val chatHistory = getChatForAddiction(message.addictionId).firstOrNull() ?: emptyList()
            val request = makeRequest(chatHistory)
            val geminiAnswer = geminiRepository.getGeminiResponseStream(request)
            val text =  geminiAnswer.joinToString("") { response ->
                response.candidates
                    ?.firstOrNull()
                    ?.content
                    ?.parts
                    ?.firstOrNull()
                    ?.text
                    ?: ""
            }
            if (text.isNotBlank()) {
                val answerMessage = makeMessage(text, message.addictionId)
                insertMessage(answerMessage)
            }
            geminiAnswer
        }
        catch (e : Exception){
            when (e) {

                is retrofit2.HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    Log.e("GEMINI_HTTP", "Code: ${e.code()}")
                    Log.e("GEMINI_HTTP", "ErrorBody: $errorBody")
                }

                is java.io.IOException -> {
                    Log.e("GEMINI_NETWORK", "Network error: ${e.message}")
                }

                else -> {
                    Log.e("GEMINI_UNKNOWN", "Unknown error", e)
                }
            }
            throw e
        }
    }

    override fun getChatForAddiction(addictionId: Int): Flow<List<Message>> {
        return messageDao.getMessageByAddictionId(addictionId).map { messageMapper.dbModelToEntityList(it) }
    }

    private suspend fun insertMessage(message: Message){
        messageDao.insertMessage(messageMapper.entityToDbModel(message))
    }

    private fun makeRequest(chatHistory: List<Message>): GeminiRequest {
        val contents = chatHistory
            .takeLast(CHAT_HISTORY_MAX_SIZE)
            .map { message ->
                ContentRequest(
                    parts = listOf(
                        PartRequest(message.text)
                    )
                )
            }
        return GeminiRequest(contents)
    }

    private fun makeMessage(text : String, addictionId: Int) : Message{
        val message = Message(id = 0, text = text, role = MessageRole.GEMINI.value, addictionId = addictionId)
        return message
    }

    companion object{
        private const val CHAT_HISTORY_MAX_SIZE = 10
    }
}