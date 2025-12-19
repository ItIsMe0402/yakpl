package com.github.itisme0402.yakpl.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config : KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(
            uiModule,
            domainModule,
            networkModule,
            ktorModule,
            platformDatabaseModule,
            databaseModule,
        )
    }
}
