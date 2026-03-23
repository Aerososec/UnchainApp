package com.example.unchain.domain.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.unchain.domain.provider.TimeProvider
import com.example.unchain.domain.repositories.UserRepository

interface ChildWorkerFactory {
    fun create(context: Context, workerParameters: WorkerParameters, userRepository: UserRepository, timeProvider: TimeProvider) : ListenableWorker
}