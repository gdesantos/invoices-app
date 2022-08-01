package com.overmind.invoiceapp.domain.usecases.business

import com.overmind.invoiceapp.domain.datasources.BusinessDataSource
import com.overmind.invoiceapp.domain.entities.Business
import kotlinx.coroutines.flow.first

class AddBusiness(
    private val businessDataSource: BusinessDataSource,
    private val validateBusiness: ValidateBusiness
) {

    suspend operator fun invoke(business: Business): Result {
        if (businessDataSource.countBusiness().first() >= BUSINESS_LIMIT) {
            return Result.LimitReached
        }

        return validateBusiness(business).let {
            when (it) {
                ValidateBusiness.Result.Ok -> {
                    businessDataSource.addBusiness(business)
                    Result.Ok
                }
                ValidateBusiness.Result.InvalidName -> Result.InvalidName
                ValidateBusiness.Result.InvalidVat -> Result.InvalidVat
                ValidateBusiness.Result.InvalidAddressLine1 -> Result.InvalidAddressLine1
                ValidateBusiness.Result.InvalidPhone -> Result.InvalidPhone
                ValidateBusiness.Result.InvalidEmail -> Result.InvalidEmail
            }
        }
    }

    enum class Result {
        Ok,
        InvalidName,
        InvalidVat,
        InvalidAddressLine1,
        InvalidPhone,
        InvalidEmail,
        LimitReached
    }

    companion object {
        private const val BUSINESS_LIMIT = 10
    }
}
