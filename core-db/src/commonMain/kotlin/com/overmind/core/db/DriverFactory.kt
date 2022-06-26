package com.overmind.core.db

import com.overmind.invoiceapp.db.MyDatabase
import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory() {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): MyDatabase = MyDatabase(driverFactory.createDriver())