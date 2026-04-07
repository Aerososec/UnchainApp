package com.example.unchain.presentation.userProgressScreen.recyclerView

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unchain.R
import com.example.unchain.databinding.ItemMessageBinding
import com.example.unchain.domain.models.Addiction
import com.example.unchain.domain.models.gemini.Message
import com.example.unchain.domain.models.gemini.MessageRole
import com.example.unchain.presentation.widget.WidgetConfigAdapter
import com.example.unchain.presentation.widget.WidgetConfigDiffCallBack

class GeminiChatAdapter : ListAdapter<Message, GeminiChatAdapter.GeminiChatViewHolder>(
    GeminiChatDiffCallBack()
){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GeminiChatViewHolder {
        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return GeminiChatViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GeminiChatViewHolder,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    class GeminiChatViewHolder(val binding : ItemMessageBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(message: Message){

            binding.messageTextView.text = message.text
            val params = binding.messageContainer.layoutParams as FrameLayout.LayoutParams

            when(message.role){
                MessageRole.GEMINI.value -> {
                    params.gravity = Gravity.START
                    binding.messageContainer.setBackgroundResource(R.drawable.bg_message_gemini)
                }
                MessageRole.USER.value -> {
                    params.gravity = Gravity.END
                    binding.messageContainer.setBackgroundResource(R.drawable.bg_message_user)
                }
            }
        }
    }
}