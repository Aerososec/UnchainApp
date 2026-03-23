package com.example.unchain

import android.app.Application
import androidx.work.Configuration
import com.example.unchain.data.db.AppDataBase
import com.example.unchain.data.models.Mapper
import com.example.unchain.di.DaggerMainComponent
import com.example.unchain.di.MainComponent
import com.example.unchain.domain.usecases.InitDbUseCase
import com.example.unchain.domain.worker.MyWorkerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UnchainApp : Application(), Configuration.Provider {

    @Inject
    lateinit var myWorkerFactory: MyWorkerFactory
    lateinit var appComponent : MainComponent


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerMainComponent.factory().create(this)
        appComponent.inject(this)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(myWorkerFactory)
            .build()

}