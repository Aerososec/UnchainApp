package com.example.unchain.presentation.widget

import android.R.attr.action
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unchain.R
import com.example.unchain.UnchainApp
import com.example.unchain.databinding.ActivityWidgetConfigBinding
import com.example.unchain.domain.models.AddictionInWidget
import com.example.unchain.domain.usecases.GetAddictionsForWidgetConfig
import com.example.unchain.domain.usecases.SetAddictionForWidgetUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WidgetConfigActivity : AppCompatActivity() {

    @Inject
    lateinit var setAddictionForWidgetUseCase: SetAddictionForWidgetUseCase
    @Inject
    lateinit var getAddictionsForWidgetConfig: GetAddictionsForWidgetConfig

    private lateinit var binding: ActivityWidgetConfigBinding
    private lateinit var widgetAdapter: WidgetConfigAdapter
    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWidgetConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.widgetSelectionRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        (application as UnchainApp).appComponent.inject(this)

        setResult(RESULT_CANCELED)
        getWidgetId()
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish()
            return
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        widgetAdapter = WidgetConfigAdapter()

        widgetAdapter.addictionCardClick = {
            val addictionInWidget = AddictionInWidget(appWidgetId, it.id)

            lifecycleScope.launch {
                val result = setAddictionForWidgetUseCase.execute(addictionInWidget)
                updateWidget()
                setResult(RESULT_OK)
                finish()
            }


        }

        binding.widgetAddictionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@WidgetConfigActivity)
            adapter = widgetAdapter
        }

        lifecycleScope.launch {
            getAddictions()
        }
    }

    private suspend fun getAddictions(){
        widgetAdapter.submitList(getAddictionsForWidgetConfig.execute())
    }

    private fun getWidgetId(){
        val intent = intent
        intent?.let {
            if (it.extras != null){
                appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
                )
            }
        }
    }

    private fun updateWidget(){
        val intent = Intent(this@WidgetConfigActivity, MyWidgetProvider::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

            putExtra(
                AppWidgetManager.EXTRA_APPWIDGET_IDS,
                intArrayOf(appWidgetId)
            )
        }
        sendBroadcast(intent)
    }

}