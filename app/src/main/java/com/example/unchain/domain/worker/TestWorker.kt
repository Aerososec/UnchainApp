package com.example.unchain.domain.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.unchain.R
import com.example.unchain.domain.provider.TimeProvider
import com.example.unchain.domain.repositories.UserRepository
import com.example.unchain.domain.usecases.SetReminderUseCase
import com.example.unchain.domain.utils.isSameDay
import kotlinx.coroutines.flow.firstOrNull
import java.sql.Time
import javax.inject.Inject


class TestWorker(private val context: Context, workerParameters: WorkerParameters, private val userRepository: UserRepository, private val timeProvider: TimeProvider) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val userProgress = userRepository.getUserProgress(getAddictionId()).firstOrNull() ?: return Result.success()
        val lastCheckDate = userProgress.lastCheckDate
        if (!isSameDay(lastCheckDate, timeProvider.now())){
            showNotification()
            Log.d("WORKER_TAG", "Days not same")
        }
        else{
            Log.d("WORKER_TAG", "Days is same")
        }
        return Result.success()
    }

    private fun getAddictionId() : Int{
        return inputData.getInt(ADDICTION_ID_KEY, DEFAULT_INT_VALUE)
    }

    private fun showNotification(){
        createChannel()
        val notification = createNotification()
        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java)
        val notificationId = getAddictionId()
        notificationManager?.notify(notificationId, notification)
        Log.d("WORKER_TAG", "Show success")
    }

    private fun createNotification() : Notification{
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(CONTENT_TITLE)
            .setContentText(CONTENT_TEXT)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    private fun createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE)

            val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }


    class Factory @Inject constructor() : ChildWorkerFactory{
        override fun create(
            context: Context,
            workerParameters: WorkerParameters,
            userRepository: UserRepository,
            timeProvider: TimeProvider
        ): ListenableWorker {
            return TestWorker(context, workerParameters, userRepository, timeProvider)
        }
    }

    companion object{
        private const val CHANNEL_ID = "CHANNEL_ID_101"
        private const val CHANNEL_NAME = "UNCHAIN_CHANNEL_NAME"
        private const val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT

        private const val CONTENT_TITLE = "Ваш Unchain"
        private const val CONTENT_TEXT = "Пришло время отметить день!"

        private const val ADDICTION_ID_KEY = SetReminderUseCase.ADDICTION_ID_KEY

        private const val DEFAULT_INT_VALUE = -1
    }
}