package com.github.itisme0402.yakpl

import android.app.Application
import com.github.itisme0402.yakpl.di.initKoin

class YakplApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
