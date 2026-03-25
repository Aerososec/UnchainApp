package com.example.unchain.domain.widgetWorker

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.unchain.R
import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.usecases.GetAddictionIdByWidgetIdUseCase
import com.example.unchain.domain.usecases.GetAddictionInfoForWidgetUseCase
import com.example.unchain.domain.usecases.GetUserProgressUseCase
import com.example.unchain.domain.usecases.MarkDayFailUseCase
import com.example.unchain.domain.usecases.MarkDaySuccessUseCase
import com.example.unchain.domain.worker.ChildWorkerFactory
import com.example.unchain.presentation.widget.MyWidgetProvider
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class WidgetWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val getAddictionIdByWidgetId: GetAddictionIdByWidgetIdUseCase,
    private val getUserProgressUseCase: GetUserProgressUseCase,
    private val getAddictionInfoForWidgetUseCase: GetAddictionInfoForWidgetUseCase,
    private val markDaySuccessUseCase: MarkDaySuccessUseCase,
    private val markDayFailUseCase: MarkDayFailUseCase
) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val widgetId = inputData.getInt(WIDGET_ID_KEY, INT_UNKNOWN_VALUE)
        val action = inputData.getString(ACTION_KEY) ?: STRING_UNKNOWN_VALUE

        val addictionId = getAddictionId(widgetId) ?: return Result.retry()
        val userProgress = getUserProgressUseCase.execute(addictionId).firstOrNull()
            ?: (getUserProgressUseCase.execute(addictionId).firstOrNull()
                ?: return Result.retry())

        when (action) {
            SUCCESS_DAY_ACTION -> {
                successDay(addictionId, userProgress)
            }

            FAIL_DAY_ACTION -> {
                failDay(addictionId, userProgress)
            }
        }

        val addictionWithInfo = getAddictionInfo(addictionId)
        val views = RemoteViews(applicationContext.packageName, R.layout.widget_layout)
        setAddictionInfo(addictionWithInfo, views)

        val successIntent = makeIntent(applicationContext, widgetId, SUCCESS_DAY_ACTION)
        val failIntent = makeIntent(applicationContext, widgetId, FAIL_DAY_ACTION)

        val successPendingIntent = makePendingIntent(
            applicationContext,
            widgetId,
            successIntent,
            SUCCESS_ACTION_REQUEST_CODE
        )
        val failPendingIntent =
            makePendingIntent(applicationContext, widgetId, failIntent, FAIL_ACTION_REQUEST_CODE)

        setPendingToButton(successPendingIntent, R.id.successfulDayButton, views)
        setPendingToButton(failPendingIntent, R.id.unsuccessfulDayButton, views)

        val widgetManager = AppWidgetManager.getInstance(applicationContext)
        widgetManager.updateAppWidget(widgetId, views)

        return Result.success()
    }

    private fun makeIntent(context: Context, widgetId: Int, myAction: String): Intent {
        return Intent(context, MyWidgetProvider::class.java).apply {
            action = myAction

            putExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                widgetId
            )
        }
    }

    private fun setPendingToButton(
        pendingIntent: PendingIntent,
        buttonId: Int,
        views: RemoteViews
    ) {
        views.setOnClickPendingIntent(buttonId, pendingIntent)
    }

    private fun makePendingIntent(
        context: Context,
        widgetId: Int,
        intent: Intent,
        requestCode: Int
    ): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            widgetId + requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun setAddictionInfo(addictionWithProgress: AddictionWithProgress, views: RemoteViews) {
        views.apply {
            setTextViewText(R.id.addictionNameTextView, addictionWithProgress.name)
            setTextViewText(
                R.id.currentCountTextView,
                addictionWithProgress.currentStreak.toString()
            )

        }
    }

    private suspend fun successDay(addictionId: Int, userProgress: UserProgress) {
        markDaySuccessUseCase.execute(userProgress, addictionId)
    }

    private suspend fun failDay(addictionId: Int, userProgress: UserProgress) {
        markDayFailUseCase.execute(userProgress, addictionId)
    }

    private suspend fun getAddictionId(widgetId: Int): Int? {
        return getAddictionIdByWidgetId.execute(widgetId)
    }

    private suspend fun getAddictionInfo(addictionId: Int): AddictionWithProgress {
        return getAddictionInfoForWidgetUseCase.execute(addictionId)
    }

    companion object {
        const val ACTION_KEY = "ACTION_KEY"
        const val WIDGET_ID_KEY = "WIDGET_ID_KEY"

        private const val INT_UNKNOWN_VALUE = -1
        private const val STRING_UNKNOWN_VALUE = "STRING_UNKNOWN_VALUE"

        private const val SUCCESS_DAY_ACTION = "com.example.unchain.SUCCESS_DAY"
        private const val FAIL_DAY_ACTION = "com.example.unchain.FAIL_DAY"

        private const val SUCCESS_ACTION_REQUEST_CODE = 1
        private const val FAIL_ACTION_REQUEST_CODE = 2
    }

    class Factory @Inject constructor(
        private val getAddictionIdByWidgetId: GetAddictionIdByWidgetIdUseCase,
        private val getUserProgressUseCase: GetUserProgressUseCase,
        private val getAddictionInfoForWidgetUseCase: GetAddictionInfoForWidgetUseCase,
        private val markDaySuccessUseCase: MarkDaySuccessUseCase,
        private val markDayFailUseCase: MarkDayFailUseCase
    ) : ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return WidgetWorker(
                context,
                workerParameters,
                getAddictionIdByWidgetId,
                getUserProgressUseCase,
                getAddictionInfoForWidgetUseCase,
                markDaySuccessUseCase,
                markDayFailUseCase
            )
        }
    }
}
