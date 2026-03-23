package com.example.unchain.presentation.widget

import android.appwidget.AppWidgetManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unchain.databinding.AddictionCardForWidgetBinding
import com.example.unchain.domain.models.Addiction
import javax.inject.Inject

class WidgetConfigAdapter : ListAdapter<Addiction, WidgetConfigAdapter.WidgetConfigViewHolder>(WidgetConfigDiffCallBack()
) {

    var addictionCardClick : ((Addiction) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WidgetConfigViewHolder {

        val binding = AddictionCardForWidgetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return WidgetConfigViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WidgetConfigViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }


    inner class WidgetConfigViewHolder(val binding: AddictionCardForWidgetBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(addiction: Addiction){
            binding.addictionNameTextView.text = addiction.name

            itemView.setOnClickListener {
                addictionCardClick?.invoke(addiction)
            }
        }
    }
}