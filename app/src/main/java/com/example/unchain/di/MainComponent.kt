package com.example.unchain.di

import android.app.Application
import com.example.unchain.MainActivity
import com.example.unchain.UnchainApp
import com.example.unchain.di.annotation.ApplicationSingleton
import com.example.unchain.di.module.AddictionInWidgetModule
import com.example.unchain.di.module.DataBaseModule
import com.example.unchain.di.module.TimeProviderModule
import com.example.unchain.di.module.UserRepositoryModule
import com.example.unchain.di.module.ViewModelModule
import com.example.unchain.di.module.WorkerModule
import com.example.unchain.presentation.allAddictionsScreen.fragment.AllAddictionsFragment
import com.example.unchain.presentation.userProgressScreen.fragment.UserProgressFragment
import com.example.unchain.presentation.widget.MyWidgetProvider
import com.example.unchain.presentation.widget.WidgetConfigActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationSingleton
@Component(modules = [DataBaseModule::class, TimeProviderModule::class, UserRepositoryModule::class, ViewModelModule::class, WorkerModule::class, AddictionInWidgetModule::class])
interface MainComponent {

    fun inject(userProgressFragment: UserProgressFragment)
    fun inject(allAddictionsFragment: AllAddictionsFragment)
    fun inject(application: UnchainApp)
    fun inject(activity: MainActivity)
    fun inject(activity: WidgetConfigActivity)
    fun inject(provider : MyWidgetProvider)

    @Component.Factory
    interface MainComponentFactory {
        fun create(@BindsInstance application: Application): MainComponent
    }
}