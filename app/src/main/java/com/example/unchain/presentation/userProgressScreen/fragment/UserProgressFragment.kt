package com.example.unchain.presentation.userProgressScreen.fragment

import android.app.TimePickerDialog
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unchain.R
import com.example.unchain.UnchainApp
import com.example.unchain.databinding.FragmentUserProgressBinding
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.utils.formattingDate
import com.example.unchain.domain.utils.formattingStreak
import com.example.unchain.presentation.shopScreen.fragment.ShopFragment
import com.example.unchain.presentation.userProgressScreen.recyclerView.GeminiChatAdapter
import com.example.unchain.presentation.userProgressScreen.viewModel.MessageViewModel
import com.example.unchain.presentation.userProgressScreen.viewModel.ProgressViewModel
import com.example.unchain.presentation.widget.MyWidgetProvider
import com.example.unchain.presentation.widget.WidgetConfigActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProgressFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentUserProgressBinding
    private lateinit var progressViewModel: ProgressViewModel
    private lateinit var messageViewModel: MessageViewModel

    private lateinit var geminiChatAdapter : GeminiChatAdapter
    private var userProgressInfo: UserProgress? = null
    private var addictionId = UNKNOWN_ID
    private var addictionName = UNKNOWN_NAME

    private var shouldShowTimePicker : Boolean = true

    private var widgetId : Int? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as UnchainApp).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[ProgressViewModel::class.java]

        messageViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[MessageViewModel::class.java]

        parseArgs()
        messageViewModel.getChat(addictionId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            progressViewModel.loadProgress(addictionId)
            widgetId = progressViewModel.getWidgetIdByAddictionId(addictionId)
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                progressViewModel.userProgressFlow.collect {
                    it.userProgress?.let { progress ->
                        setValues(progress)
                        Log.d("TIME_PICKER", "Check picked: ${progress.hour} ${progress.minute}")
                    }

                    if (it.shouldShowTimePicker && shouldShowTimePicker){
                        shouldShowTimePicker = false
                        showTimePicker()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            progressViewModel.motivationFlow.collect { response ->
                binding.motivationMessageTextView.text = response?.candidates?.get(0)?.content?.parts[0]?.text
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            messageViewModel.chatFlow.collect {
                geminiChatAdapter.submitList(it)
                binding.chatRecyclerView.scrollToPosition(it.lastIndex)
            }
        }

        binding.successfulDayButton.setOnClickListener {
            userProgressInfo?.let {
                progressViewModel.markDaySuccess(it, addictionId)
                updateWidget()
            }
        }

        binding.unsuccessfulDayButton.setOnClickListener {
            userProgressInfo?.let {
                progressViewModel.markDayFail(it, addictionId)
                updateWidget()
            }
        }

        binding.getMotivationButton.setOnClickListener {
            setGeminiResponse()
        }

        binding.motivationModeButton.setOnClickListener {
            binding.motivationLayout.visibility = View.VISIBLE
            binding.chatLayout.visibility = View.GONE
        }

        binding.chatModeButton.setOnClickListener {
            binding.motivationLayout.visibility = View.GONE
            binding.chatLayout.visibility = View.VISIBLE
        }

        binding.sendMessageButton.setOnClickListener {
            val text = binding.messageEditText.text.toString()
            if (text.isNotBlank()) {
                messageViewModel.sendMessage(text, addictionId)
                binding.messageEditText.text.clear()
            }
        }

        binding.shopButton.setOnClickListener {
            openShopFragment(addictionId)
        }

    }

    private fun openShopFragment(addictionId: Int){
        val fragment = ShopFragment.newInstance(addictionId)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setGeminiResponse(){
        binding.progressBar.visibility = View.VISIBLE
        binding.motivationMessageTextView.text = "ГЕНЕРИРУЮ МОТИВАЦИОННУЮ РЕЧЬ..."

        val progress = userProgressInfo ?: return
        progressViewModel.getGeminiMotivationResponse(progress, addictionName)

    }

    private fun updateWidget(){
        widgetId?.let {
            val intent = Intent(context, MyWidgetProvider::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

                putExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    intArrayOf(it)
                )
            }
            context?.sendBroadcast(intent)
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        parseId(args)
        parseName(args)
    }

    private fun parseId(args: Bundle) {
        if (!args.containsKey(ADDICTION_ID)) {
            throw RuntimeException("Unknown key for id")
        }

        val id = args.getInt(ADDICTION_ID)
        if (id < 0) {
            throw RuntimeException("Incorrect ID")
        }

        addictionId = id
    }

    private fun parseName(args: Bundle) {
        if (!args.containsKey(ADDICTION_NAME)) {
            throw RuntimeException("Unknown key for name")
        }
        addictionName = args.getString(ADDICTION_NAME).toString()
    }


    private fun setValues(userProgress: UserProgress?) {
        userProgressInfo = userProgress?.apply {
            binding.addictionNameTextView.text = addictionName
            binding.currentRecordValue.text = userProgress.currentStreak.formattingStreak()
            binding.bestRecordValue.text = userProgress.bestStreak.formattingStreak()
            binding.currencyAmountTextView.text = userProgress.currency.toString()
            binding.startDateTextView.text = userProgress.startDate.formattingDate()
        }
    }

    private fun setUpRecyclerView(){
        geminiChatAdapter = GeminiChatAdapter()

        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = geminiChatAdapter
        }

    }




    private fun showTimePicker(){
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timeSetListener = TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
            progressViewModel.setReminder(addictionId, hour, minute)
        }

        val timePickerDialog = TimePickerDialog(
            requireContext(),          // Если мы во Fragment. Если в Activity -> this
            0,                         // 0 означает стандартную тему
            timeSetListener,           // Наш слушатель
            currentHour,               // Начальный час
            currentMinute,             // Начальная минута
            true                       // true = 24-часовой формат (13:00), false = 1:00 PM
        )

        timePickerDialog.show()
    }

    companion object {
        private const val ADDICTION_ID = "addiction_ID"
        private const val ADDICTION_NAME = "addiction_name"

        private const val UNKNOWN_ID = -1
        private const val UNKNOWN_NAME = "UNKNOWN_ADDICTION"

        fun newInstanceUserProgress(addictionId: Int, addictionName: String): UserProgressFragment {
            return UserProgressFragment().apply {
                arguments = Bundle().apply {
                    putInt(ADDICTION_ID, addictionId)
                    putString(ADDICTION_NAME, addictionName)
                }
            }
        }
    }
}