package com.example.unchain.presentation.shopScreen.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unchain.databinding.ItemPersonalityBinding
import com.example.unchain.domain.models.personalization.Personality

class ShopAdapter : ListAdapter<Personality, ShopAdapter.ShopItemViewHolder>(ShopDiffUtil()){
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

    class ShopItemViewHolder(val binding : ItemPersonalityBinding) : RecyclerView.ViewHolder(binding.root){

        fun onBind(item : Personality){
            binding.tvName.text = item.name
            binding.tvDescription.text = item.description
            binding.tvPrice.text = item.price.toString() + " coins"
        }
    }
}