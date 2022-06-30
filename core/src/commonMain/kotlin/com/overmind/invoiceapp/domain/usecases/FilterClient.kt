package com.overmind.invoiceapp.domain.usecases

import com.overmind.invoiceapp.domain.entities.Client

class FilterClient {

    operator fun invoke(client: Client, filter: String): Boolean =
        client.name.contains(filter, ignoreCase = true) ||
            client.email.contains(filter, ignoreCase = true) ||
            client.vat.contains(filter, ignoreCase = true) ||
            client.phone.toString().contains(filter) ||
            client.addressLine1.contains(filter, ignoreCase = true) ||
            client.addressLine2.contains(filter, ignoreCase = true)
}
