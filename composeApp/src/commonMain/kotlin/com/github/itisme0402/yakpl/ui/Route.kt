package com.github.itisme0402.yakpl.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    object Products : Route()

    @Serializable
    object Details : Route()
}
