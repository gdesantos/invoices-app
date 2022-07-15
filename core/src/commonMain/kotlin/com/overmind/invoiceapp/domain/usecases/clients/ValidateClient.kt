package com.overmind.invoiceapp.domain.usecases.clients

import com.overmind.invoiceapp.domain.ValidationRegex
import com.overmind.invoiceapp.domain.entities.Client

class ValidateClient {

    operator fun invoke(client: Client): Result =
        when {
            client.name.isEmpty() -> Result.InvalidName
            client.vat.isEmpty() -> Result.InvalidVat
            client.addressLine1.isEmpty() -> Result.InvalidAddressLine1
            client.phone.isNotEmpty() && !ValidationRegex.phone.matches(client.phone) -> Result.InvalidPhone
            client.email.isNotEmpty() && !ValidationRegex.mail.matches(client.email) -> Result.InvalidEmail
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
