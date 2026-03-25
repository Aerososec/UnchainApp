package com.example.unchain.presentation.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.unchain.R
import com.example.unchain.UnchainApp
import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.usecases.GetAddictionIdByWidgetIdUseCase
import com.example.unchain.domain.usecases.GetAddictionInfoForWidgetUseCase
import com.example.unchain.domain.usecases.GetUserProgressUseCase
import com.example.unchain.domain.usecases.MarkDayFailUseCase
import com.example.unchain.domain.usecases.MarkDaySuccessUseCase
import com.example.unchain.domain.widgetWorker.WidgetWorker
import com.example.unchain.domain.widgetWorker.WidgetWorker.Companion.ACTION_KEY
import com.example.unchain.domain.widgetWorker.WidgetWorker.Companion.WIDGET_ID_KEY
import com.example.unchain.presentation.widget.WidgetConfigActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyWidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent != null && context != null) {

            (context.applicationContext as UnchainApp).appComponent.inject(this)

            val widgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                -1
            )

            val action = intent.action

            startWorker(context, widgetId, action)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        (context.applicationContext as UnchainApp).appComponent.inject(this)
        updateWidget(context, appWidgetManager, appWidgetIds)
    }

    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            for (id in appWidgetIds) {
                startWorker(context, id, null)
            }
        }
    }

    private fun startWorker(context: Context, widgetId: Int, action: String?) {
        val inputData = workDataOf(
            ACTION_KEY to action,
            WIDGET_ID_KEY to widgetId
        )

        val request = OneTimeWorkRequestBuilder<WidgetWorker>()
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context)
            .enqueue(request)
    }

}