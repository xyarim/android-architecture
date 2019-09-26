package com.xyarim.users

import android.app.Application
import com.xyarim.users.di.remoteDatasourceModule
import com.xyarim.users.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(remoteDatasourceModule + viewModelsModule)
        }
    }

}