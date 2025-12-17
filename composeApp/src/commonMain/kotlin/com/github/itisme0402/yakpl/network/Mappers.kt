package com.github.itisme0402.yakpl.network

import com.github.itisme0402.yakpl.model.Product

val RemoteProduct.asProduct
    get() = Product(
        id = id.toString(),
        name = title,
        description = description,
    )
