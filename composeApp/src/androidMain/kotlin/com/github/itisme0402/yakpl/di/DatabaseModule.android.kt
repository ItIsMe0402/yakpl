package com.github.itisme0402.yakpl.di

import androidx.room.RoomDatabase
import com.github.itisme0402.yakpl.db.AppDatabase
import com.github.itisme0402.yakpl.db.getDatabaseBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

actual val platformDatabaseModule = module {
    factory<RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder(androidApplication()) }
}
