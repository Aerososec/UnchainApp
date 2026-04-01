package com.example.unchain.data.models

import com.example.unchain.domain.models.gemini.Message
import javax.inject.Inject

class MessageMapper @Inject constructor(){
    fun entityToDbModel(message: Message) : MessageDbModel{
        return MessageDbModel(
            message.id,
            message.text,
            message.role,
            message.addictionId
        )
    }

    fun dbModelToEntity(message: MessageDbModel) : Message{
        return Message(
            message.id,
            message.text,
            message.role,
            message.addictionId
        )
    }

    fun entityToDbModelList(messageList : List<Message>) : List<MessageDbModel>{
        return messageList.map { entityToDbModel(it) }
    }

    fun dbModelToEntityList(messageDbModelList : List<MessageDbModel>) : List<Message>{
        return messageDbModelList.map { dbModelToEntity(it) }
    }
}