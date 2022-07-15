package com.overmind.invoiceapp.domain.entities

data class Business(
    val id: Int,
    val name: String,
    val vat: String,
    val addressLine1: String,
    val addressLine2: String,
    val phone: String,
    val email: String,
    val logo: ByteArray?
)