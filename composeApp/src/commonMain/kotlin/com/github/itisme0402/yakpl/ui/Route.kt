package com.github.itisme0402.yakpl.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    object Products : Route()

    @Serializable
    data class Details(val productId: String) : Route()
}
