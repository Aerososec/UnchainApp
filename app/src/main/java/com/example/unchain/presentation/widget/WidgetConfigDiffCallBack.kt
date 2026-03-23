package com.example.unchain.presentation.widget

import androidx.recyclerview.widget.DiffUtil
import com.example.unchain.domain.models.Addiction

class WidgetConfigDiffCallBack : DiffUtil.ItemCallback<Addiction>() {
    override fun areItemsTheSame(
        oldItem: Addiction,
        newItem: Addiction
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Addiction,
        newItem: Addiction
    ): Boolean {
        return oldItem == newItem
    }

}