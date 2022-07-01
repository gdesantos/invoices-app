package com.overmind.invoiceapp.domain.usecases

import com.overmind.invoiceapp.domain.entities.Client

class ValidateClient {

    operator fun invoke(client: Client): Result =
        when {
            client.name.isEmpty() -> Result.InvalidName
            client.vat.isEmpty() -> Result.InvalidVat
            client.addressLine1.isEmpty() -> Result.InvalidAddressLine1
            client.phone.isNotEmpty() && !PHONE_REGEX.matches(client.phone) -> Result.InvalidPhone
            client.email.isNotEmpty() && !MAIL_REGEX.matches(client.email) -> Result.InvalidEmail
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

    companion object {
        private val MAIL_REGEX =
            """(?:[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""".toRegex()
        private val PHONE_REGEX =
            """^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}${'$'}""".toRegex()
    }
}
