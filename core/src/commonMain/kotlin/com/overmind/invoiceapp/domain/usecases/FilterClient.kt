package com.overmind.invoiceapp.domain.usecases

import com.overmind.invoiceapp.domain.entities.Client

class FilterClient {

    operator fun invoke(client: Client, filter: String): Boolean =
        client.name.contains(filter) ||
            client.email.contains(filter) ||
            client.vat.contains(filter) ||
            client.phone.toString().contains(filter) ||
            client.addressLine1.contains(filter) ||
            client.addressLine2.contains(filter)
}
