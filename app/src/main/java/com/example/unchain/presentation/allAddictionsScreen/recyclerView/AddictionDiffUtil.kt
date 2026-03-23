package com.example.unchain.presentation.allAddictionsScreen.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.example.unchain.domain.models.AddictionWithProgress

class AddictionDiffUtil(private val oldList: List<AddictionWithProgress>, private val newList: List<AddictionWithProgress>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}