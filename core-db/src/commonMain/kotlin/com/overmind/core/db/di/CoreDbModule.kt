package com.overmind.core.db.di

import com.overmind.core.db.DriverFactory
import com.overmind.core.db.createDatabase
import org.koin.dsl.module

val coreDbModule = module {
    single { createDatabase(DriverFactory()) }
}