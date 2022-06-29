package com.overmind.invoiceapp.domain.usecases

import com.overmind.invoiceapp.domain.entities.Client

class ValidateClient {

    operator fun invoke(client: Client): Result =
        when {
            client.name.isEmpty() -> Result.InvalidName
            client.vat.isEmpty() -> Result.InvalidVat
            client.addressLine1.isEmpty() -> Result.InvalidAddressLine1
            client.phone == 0L -> Result.InvalidPhone
            client.email.isEmpty() -> Result.InvalidEmail
            else -> Result.Ok
        }

    enum class Result {
        Ok,
        InvalidName,
        InvalidVat,
        InvalidAddressLine1,
        InvalidPhone,
        InvalidEmail
    }
}
