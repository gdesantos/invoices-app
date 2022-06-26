package com.overmind.core.db

import com.overmind.invoiceapp.db.MyDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MyDatabase.Schema, "mydatabase.db")
    }
}