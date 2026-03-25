package com.example.unchain.di.module

import com.example.unchain.di.annotation.WorkerKey
import com.example.unchain.domain.widgetWorker.WidgetWorker
import com.example.unchain.domain.worker.ChildWorkerFactory
import com.example.unchain.domain.worker.TestWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(TestWorker::class)
    fun bindTestWorker(factory: TestWorker.Factory) : ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(WidgetWorker::class)
    fun bindWidgetWorker(factory: WidgetWorker.Factory) : ChildWorkerFactory
}