package com.overmind.invoiceapp

import android.app.Application
import com.overmind.core.db.di.businessDbModule
import com.overmind.core.db.di.clientsDbModule
import com.overmind.core.db.di.coreDbModule
import com.overmind.invoiceapp.android.di.viewModelsModule
import com.overmind.invoiceapp.domain.di.businessDomainModule
import com.overmind.invoiceapp.domain.di.clientsDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class InvoiceApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@InvoiceApplication)

            modules(coreDbModule)
            modules(clientsDomainModule, clientsDbModule)
            modules(businessDomainModule, businessDbModule)

            modules(viewModelsModule)
        }
    }
}
