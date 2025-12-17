package com.github.itisme0402.yakpl.network

import kotlinx.serialization.Serializable

@Serializable
data class RemoteProduct(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val discountPercentage: String,
    val rating: String,
    val stock: Int,
)
