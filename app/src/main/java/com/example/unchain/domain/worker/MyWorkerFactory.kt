package com.example.unchain.domain.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.unchain.domain.provider.TimeProvider
import com.example.unchain.domain.repositories.UserRepository
import com.example.unchain.domain.widgetWorker.WidgetWorker
import javax.inject.Inject
import javax.inject.Provider

class MyWorkerFactory @Inject constructor(private val workers: @JvmSuppressWildcards Map<Class<out ListenableWorker>, Provider<ChildWorkerFactory>>, private val userRepository: UserRepository, private val timeProvider: TimeProvider) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName){
            TestWorker::class.qualifiedName -> {
                val childWorkerFactory = workers[TestWorker::class.java]?.get()
                childWorkerFactory?.create(appContext, workerParameters)
            }
            WidgetWorker::class.qualifiedName -> {
                val childWorkerFactory = workers[WidgetWorker::class.java]?.get()
                childWorkerFactory?.create(appContext, workerParameters)
            }
            else -> null
        }
    }
}