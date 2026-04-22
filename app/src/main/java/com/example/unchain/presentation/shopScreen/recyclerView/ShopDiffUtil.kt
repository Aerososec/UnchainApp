package com.example.unchain.presentation.shopScreen.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.unchain.domain.models.personalization.Personality

class ShopDiffUtil : DiffUtil.ItemCallback<Personality>() {
    override fun areItemsTheSame(
        oldItem: Personality,
        newItem: Personality
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Personality,
        newItem: Personality
    ): Boolean {
        return oldItem == newItem
    }

}