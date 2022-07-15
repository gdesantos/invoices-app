package com.overmind.invoiceapp.domain.usecases.business

import com.overmind.invoiceapp.domain.ValidationRegex
import com.overmind.invoiceapp.domain.entities.Business

class ValidateBusiness {

    operator fun invoke(business: Business): Result =
        when {
            business.name.isEmpty() -> Result.InvalidName
            business.vat.isEmpty() -> Result.InvalidVat
            business.addressLine1.isEmpty() -> Result.InvalidAddressLine1
            business.phone.isNotEmpty() && !ValidationRegex.phone.matches(business.phone) -> Result.InvalidPhone
            business.email.isNotEmpty() && !ValidationRegex.mail.matches(business.email) -> Result.InvalidEmail
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