package com.overmind.invoiceapp.domain.entities

data class Client(
    val id: Int,
    val name: String,
    val vat: String,
    val addressLine1: String,
    val addressLine2: String,
    val phone: Long,
    val email: String
)