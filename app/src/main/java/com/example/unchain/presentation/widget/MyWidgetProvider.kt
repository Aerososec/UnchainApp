package com.example.unchain.presentation.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.example.unchain.R
import com.example.unchain.UnchainApp
import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.usecases.GetAddictionIdByWidgetIdUseCase
import com.example.unchain.domain.usecases.GetAddictionInfoForWidgetUseCase
import com.example.unchain.domain.usecases.GetUserProgressUseCase
import com.example.unchain.domain.usecases.MarkDayFailUseCase
import com.example.unchain.domain.usecases.MarkDaySuccessUseCase
import com.example.unchain.presentation.widget.WidgetConfigActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyWidgetProvider : AppWidgetProvider() {

    @Inject
    lateinit var getAddictionIdByWidgetId: GetAddictionIdByWidgetIdUseCase
    @Inject
    lateinit var getUserProgressUseCase: GetUserProgressUseCase
    @Inject
    lateinit var getAddictionInfoForWidgetUseCase: GetAddictionInfoForWidgetUseCase
    @Inject
    lateinit var markDaySuccessUseCase: MarkDaySuccessUseCase
    @Inject
    lateinit var markDayFailUseCase: MarkDayFailUseCase


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent != null && context != null){

            (context.applicationContext as UnchainApp).appComponent.inject(this)

            val widgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                -1
            )

            CoroutineScope(Dispatchers.IO).launch{
                val addictionId = getAddictionId(widgetId)

                addictionId?.let {


                    val userProgress = getUserProgressUseCase.execute(addictionId).firstOrNull()
                    userProgress?.let {

                        when(intent.action){
                            SUCCESS_DAY_ACTION -> {
                                successDayAction(addictionId, userProgress)
                            }
                            FAIL_DAY_ACTION -> {
                                failDayAction(addictionId, userProgress)
                            }
                        }

                        updateWidget(context, widgetId)
                    }

                }

            }
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

                val views = RemoteViews(context.packageName, R.layout.widget_layout)
                val addictionId = getAddictionId(widgetId = id)
                addictionId?.let { addictionId ->
                    val addictionInfo = getAddictionInfo(addictionId)
                    setAddictionInfo(addictionInfo, views)
                    Toast.makeText(context, "${addictionInfo.name} ${addictionInfo.currentStreak}",
                        Toast.LENGTH_SHORT).show()
                }

                val successIntent = makeIntent(context, id, SUCCESS_DAY_ACTION)
                val failIntent = makeIntent(context, id, FAIL_DAY_ACTION)

                val successPendingIntent = makePendingIntent(context, id, successIntent, SUCCESS_ACTION_REQUEST_CODE)
                val failPendingIntent = makePendingIntent(context, id, failIntent, FAIL_ACTION_REQUEST_CODE)

                setPendingToButton(successPendingIntent, R.id.successfulDayButton, views)
                setPendingToButton(failPendingIntent, R.id.unsuccessfulDayButton, views)

                appWidgetManager.updateAppWidget(id, views)
            }
        }
    }

    private suspend fun successDayAction(addictionId : Int, userProgress: UserProgress){
        markDaySuccessUseCase.execute(userProgress, addictionId)
    }

    private suspend fun failDayAction(addictionId : Int, userProgress: UserProgress){
        markDayFailUseCase.execute(userProgress, addictionId)
    }

    private suspend fun getAddictionInfo(addictionId : Int) : AddictionWithProgress{
        getUserProgressUseCase.execute(addictionId).firstOrNull() // начинаем ведение зависимости, если она не начата
        return getAddictionInfoForWidgetUseCase.execute(addictionId)
    }

    private suspend fun getAddictionId(widgetId : Int) : Int?{
        return getAddictionIdByWidgetId.execute(widgetId)
    }

    private fun setAddictionInfo(addictionWithProgress: AddictionWithProgress, views : RemoteViews){
        views.apply{
            setTextViewText(R.id.addictionNameTextView, addictionWithProgress.name)
            setTextViewText(R.id.currentCountTextView, addictionWithProgress.currentStreak.toString())

        }
    }

    private fun makeIntent(context: Context, widgetId : Int, myAction : String) : Intent{
        return Intent(context, MyWidgetProvider::class.java).apply {
            action = myAction

            putExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                widgetId
            )
        }
    }

    private fun setPendingToButton(pendingIntent: PendingIntent, buttonId : Int, views : RemoteViews){
        views.setOnClickPendingIntent(buttonId, pendingIntent)
    }

    private fun makePendingIntent(context: Context, widgetId : Int, intent: Intent, requestCode : Int) : PendingIntent{
        return PendingIntent.getBroadcast(
            context,
            widgetId + requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun updateWidget(context: Context, id : Int){
        val intent = Intent(context, MyWidgetProvider::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

            putExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                id
            )
        }
        context.sendBroadcast(intent)
    }

    companion object{
        private const val SUCCESS_DAY_ACTION = "com.example.unchain.SUCCESS_DAY"
        private const val FAIL_DAY_ACTION = "com.example.unchain.FAIL_DAY"

        private const val SUCCESS_ACTION_REQUEST_CODE = 1
        private const val FAIL_ACTION_REQUEST_CODE = 2
    }

}