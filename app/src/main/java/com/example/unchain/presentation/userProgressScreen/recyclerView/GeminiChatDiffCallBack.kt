package com.example.unchain.presentation.userProgressScreen.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.unchain.domain.models.gemini.Message

class GeminiChatDiffCallBack : DiffUtil.ItemCallback<Message>(){
    override fun areItemsTheSame(
        oldItem: Message,
        newItem: Message
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Message,
        newItem: Message
    ): Boolean {
        return oldItem == newItem
    }

}