package com.example.unchain.domain.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.unchain.domain.provider.TimeProvider
import com.example.unchain.domain.repositories.UserRepository
import com.example.unchain.domain.usecases.GetAddictionIdByWidgetIdUseCase
import com.example.unchain.domain.usecases.GetAddictionInfoForWidgetUseCase
import com.example.unchain.domain.usecases.GetUserProgressUseCase
import com.example.unchain.domain.usecases.MarkDayFailUseCase
import com.example.unchain.domain.usecases.MarkDaySuccessUseCase

interface ChildWorkerFactory {
    fun create(
        context: Context,
        workerParameters: WorkerParameters
    ): ListenableWorker


}