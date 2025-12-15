package com.github.itisme0402.yakpl

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform