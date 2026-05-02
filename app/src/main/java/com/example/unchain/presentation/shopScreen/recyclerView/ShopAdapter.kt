package com.example.unchain.presentation.shopScreen.recyclerView

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unchain.databinding.ItemPersonalityBinding
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.models.personalization.PersonalityState

class ShopAdapter : ListAdapter<Personality, ShopAdapter.ShopItemViewHolder>(ShopDiffUtil()){

    var itemClick : ((Personality) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopItemViewHolder {
        val binding = ItemPersonalityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ShopItemViewHolder,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    inner class ShopItemViewHolder(val binding : ItemPersonalityBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(item : Personality){
            binding.tvName.text = item.name
            binding.tvDescription.text = item.description
            binding.tvPrice.text = item.price.toString() + " coins"

            binding.root.setOnClickListener {
                itemClick?.invoke(item)
            }

            when(item.state){
                PersonalityState.LOCKED.state -> {
                    binding.personalityBackround.setBackgroundColor(Color.RED)
                }
                PersonalityState.UNLOCKED_SELECTED.state -> {
                    binding.personalityBackround.setBackgroundColor(Color.WHITE)
                }
                PersonalityState.UNLOCKED_NOT_SELECTED.state -> {
                    binding.personalityBackround.setBackgroundColor(Color.GRAY)
                }
            }
        }
    }
}