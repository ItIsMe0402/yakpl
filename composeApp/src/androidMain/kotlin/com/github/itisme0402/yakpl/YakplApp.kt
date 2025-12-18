package com.github.itisme0402.yakpl

import android.app.Application
import com.github.itisme0402.yakpl.di.initKoin
import org.koin.android.ext.koin.androidContext

class YakplApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@YakplApp)
        }
    }
}
