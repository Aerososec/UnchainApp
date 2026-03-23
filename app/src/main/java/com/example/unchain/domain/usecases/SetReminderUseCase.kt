package com.example.unchain.domain.usecases

import android.app.Application
import android.content.Context
import android.icu.util.TimeUnit
import android.provider.ContactsContract
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.unchain.domain.repositories.UserRepository
import com.example.unchain.domain.worker.TestWorker
import kotlinx.coroutines.flow.firstOrNull
import java.sql.Time
import java.util.Calendar
import javax.inject.Inject


class SetReminderUseCase @Inject constructor(private val userRepository: UserRepository, private val getUserProgressUseCase: GetUserProgressUseCase, private val context: Application) {
    suspend fun execute(addictionId : Int, hour : Int, minute : Int){
        val userProgress = getUserProgressUseCase.execute(addictionId).firstOrNull()
        if (userProgress != null){
            val newUserProgress = userProgress.copy(hour = hour, minute = minute)
            startWorker(hour, minute, context, addictionId)
            userRepository.saveUserProgress(newUserProgress)
        }
    }

    private fun startWorker(hour: Int, minute: Int, context: Application, addictionId: Int){
        val delay = calculateInitialDelay(hour, minute)
        val data = makeData(addictionId)

        val request = PeriodicWorkRequestBuilder<TestWorker>(10, java.util.concurrent.TimeUnit.SECONDS)
            .setInitialDelay(delay, java.util.concurrent.TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )
    }

    private fun makeData(addictionId: Int) : Data{
        return Data.Builder()
            .putInt(ADDICTION_ID_KEY, addictionId)
            .build()
    }

    private fun calculateInitialDelay(hour: Int, minute: Int): Long {

        val now = Calendar.getInstance()

        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (target.before(now)) {
            target.add(Calendar.DAY_OF_MONTH, 1)
        }

        return target.timeInMillis - now.timeInMillis
    }

    companion object{
        private const val WORK_NAME = "UNCHAIN_REMIND_WORK"
        const val ADDICTION_ID_KEY = "ADDICTION_ID_KEY"
    }
}