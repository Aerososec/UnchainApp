package com.example.unchain.presentation.allAddictionsScreen.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unchain.R
import com.example.unchain.domain.models.AddictionWithProgress

class AllAddictionsAdapter() : RecyclerView.Adapter<AllAddictionsAdapter.AddictionViewHolder>() {

    private var addictionsList: List<AddictionWithProgress> = emptyList()
    var startButtonClick: ((AddictionWithProgress) -> Unit)? = null
    var progressAddictionCardClick :((AddictionWithProgress) -> Unit)? = null

    fun submitList(list: List<AddictionWithProgress>) {
        val addictionDiffUtil = AddictionDiffUtil(addictionsList, list)
        val resultDiffUtil = DiffUtil.calculateDiff(addictionDiffUtil)

        addictionsList = list
        resultDiffUtil.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddictionViewHolder {

        val resource = getResource(viewType)
        val view =
            LayoutInflater.from(parent.context).inflate(resource, parent, false)

        return if (viewType == START_THEME) StartAddictionViewHolder(view) else ProgressAddictionViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AddictionViewHolder,
        position: Int
    ) {
        holder.bind(addictionsList[position])

        if (addictionsList[position].isActive){
            (holder as ProgressAddictionViewHolder).view.setOnClickListener {
                progressAddictionCardClick?.invoke(addictionsList[position])
            }
        }
    }

    override fun getItemCount(): Int = addictionsList.size

    override fun getItemViewType(position: Int): Int {
        return if (addictionsList[position].isActive){
            PROGRESS_THEME
        }
        else{
            START_THEME
        }
    }

    private fun getResource(viewType: Int) : Int{
        return when(viewType){
            START_THEME -> {
                R.layout.addiction_card
            }
            PROGRESS_THEME -> {
                R.layout.addiction_card_progress
            }
            else -> {
                throw RuntimeException("Unknown ViewType")
            }
        }
    }

    companion object{
        private const val START_THEME = 100
        private const val PROGRESS_THEME = 101
    }

    abstract class AddictionViewHolder(view: View) : RecyclerView.ViewHolder(view){
        abstract fun bind(addiction: AddictionWithProgress)
    }


    inner class StartAddictionViewHolder(view: View) : AddictionViewHolder(view) {
        private val addictionName: TextView = view.findViewById<TextView>(R.id.addictionNameTextView)
        private val startButton: Button = view.findViewById<Button>(R.id.startButton)

        override fun bind(addiction: AddictionWithProgress) {
            addictionName.text = addiction.name
            startButton.setOnClickListener {
                startButtonClick?.invoke(addiction)
            }
        }
    }

    inner class ProgressAddictionViewHolder(val view : View) : AddictionViewHolder(view){
        private val addictionName: TextView = view.findViewById<TextView>(R.id.addictionNameTextView)
        private val progress: TextView = view.findViewById<TextView>(R.id.progressTextView)

        override fun bind(addiction: AddictionWithProgress) {
            addictionName.text = addiction.name
            progress.text = addiction.currentStreak.toString()
        }
    }

}