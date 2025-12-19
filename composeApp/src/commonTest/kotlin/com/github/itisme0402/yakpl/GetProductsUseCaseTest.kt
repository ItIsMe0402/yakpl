package com.github.itisme0402.yakpl

import com.github.itisme0402.yakpl.di.domainModule
import com.github.itisme0402.yakpl.di.networkModule
import com.github.itisme0402.yakpl.domain.GetProductsUseCase
import com.github.itisme0402.yakpl.network.httpClientConfigurationBlock
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductsUseCaseTest : KoinTest {

    private lateinit var mockRequestHandler: MockRequestHandler

    @BeforeTest
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(
                domainModule,
                networkModule,
                module {
                    single {
                        HttpClient(
                            MockEngine.create {
                                requestHandlers.add(mockRequestHandler)

                                dispatcher = testDispatcher
                            },
                            httpClientConfigurationBlock(get())
                        )
                    }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `requests products from API correctly`() = runTest {
        mockRequestHandler = { request ->
            val url = request.url
            assertTrue(url.toString().startsWith("https://dummyjson.com/products/search"))
            assertTrue(url.parameters.contains("q", "ab"))
            assertTrue(url.parameters.contains("skip", "13"))
            assertTrue(url.parameters.contains("limit", "5"))

            respond(
                content = TEST_PRODUCT_LIST_JSON,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    "application/json"
                ),
            )
        }

        val sut = get<GetProductsUseCase>()
        val result = sut.getProducts("ab", 13, 5)

        val names = result.map { it.name }
        assertEquals(
            listOf(
                "Carbon Steel Wok",
                "Chopping Board",
                "Electric Stove",
                "Fork",
                "Glass"
            ),
            names
        )
    }
}

private val TEST_PRODUCT_LIST_JSON = """
    {
      "products": [
        {
          "id": 52,
          "title": "Carbon Steel Wok",
          "description": "The Carbon Steel Wok is a versatile cooking pan suitable for stir-frying, sautéing, and deep frying. Its sturdy construction ensures even heat distribution for delicious meals.",
          "category": "kitchen-accessories",
          "price": 29.99,
          "discountPercentage": 6.53,
          "rating": 4.05,
          "stock": 40,
          "tags": [
            "cookware",
            "woks"
          ],
          "sku": "KIT-BRD-CAR-052",
          "weight": 2,
          "dimensions": {
            "width": 27.69,
            "height": 7.54,
            "depth": 10.11
          },
          "warrantyInformation": "2 year warranty",
          "shippingInformation": "Ships in 1-2 business days",
          "availabilityStatus": "In Stock",
          "reviews": [
            {
              "rating": 4,
              "comment": "Excellent quality!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Nolan Bryant",
              "reviewerEmail": "nolan.bryant@x.dummyjson.com"
            },
            {
              "rating": 4,
              "comment": "Highly impressed!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Luna Perez",
              "reviewerEmail": "luna.perez@x.dummyjson.com"
            },
            {
              "rating": 5,
              "comment": "Very satisfied!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Noah Lewis",
              "reviewerEmail": "noah.lewis@x.dummyjson.com"
            }
          ],
          "returnPolicy": "7 days return policy",
          "minimumOrderQuantity": 9,
          "meta": {
            "createdAt": "2025-04-30T09:41:02.053Z",
            "updatedAt": "2025-04-30T09:41:02.053Z",
            "barcode": "1810862118199",
            "qrCode": "https://cdn.dummyjson.com/public/qr-code.png"
          },
          "images": [
            "https://cdn.dummyjson.com/product-images/kitchen-accessories/carbon-steel-wok/1.webp"
          ],
          "thumbnail": "https://cdn.dummyjson.com/product-images/kitchen-accessories/carbon-steel-wok/thumbnail.webp"
        },
        {
          "id": 53,
          "title": "Chopping Board",
          "description": "The Chopping Board is an essential kitchen accessory for food preparation. Made from durable material, it provides a safe and hygienic surface for cutting and chopping.",
          "category": "kitchen-accessories",
          "price": 12.99,
          "discountPercentage": 8.03,
          "rating": 3.7,
          "stock": 14,
          "tags": [
            "kitchen tools",
            "cutting boards"
          ],
          "sku": "KIT-BRD-CHO-053",
          "weight": 2,
          "dimensions": {
            "width": 15.6,
            "height": 6.93,
            "depth": 8.51
          },
          "warrantyInformation": "3 months warranty",
          "shippingInformation": "Ships in 2 weeks",
          "availabilityStatus": "In Stock",
          "reviews": [
            {
              "rating": 5,
              "comment": "Great value for money!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Xavier Wright",
              "reviewerEmail": "xavier.wright@x.dummyjson.com"
            },
            {
              "rating": 4,
              "comment": "Great value for money!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Henry Turner",
              "reviewerEmail": "henry.turner@x.dummyjson.com"
            },
            {
              "rating": 4,
              "comment": "Fast shipping!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Evelyn Walker",
              "reviewerEmail": "evelyn.walker@x.dummyjson.com"
            }
          ],
          "returnPolicy": "No return policy",
          "minimumOrderQuantity": 5,
          "meta": {
            "createdAt": "2025-04-30T09:41:02.053Z",
            "updatedAt": "2025-04-30T09:41:02.053Z",
            "barcode": "0085585730728",
            "qrCode": "https://cdn.dummyjson.com/public/qr-code.png"
          },
          "images": [
            "https://cdn.dummyjson.com/product-images/kitchen-accessories/chopping-board/1.webp"
          ],
          "thumbnail": "https://cdn.dummyjson.com/product-images/kitchen-accessories/chopping-board/thumbnail.webp"
        },
        {
          "id": 56,
          "title": "Electric Stove",
          "description": "The Electric Stove provides a portable and efficient cooking solution. Ideal for small kitchens or as an additional cooking surface for various culinary needs.",
          "category": "kitchen-accessories",
          "price": 49.99,
          "discountPercentage": 14.04,
          "rating": 4.11,
          "stock": 21,
          "tags": [
            "kitchen appliances",
            "cooktops"
          ],
          "sku": "KIT-BRD-ELE-056",
          "weight": 5,
          "dimensions": {
            "width": 24.17,
            "height": 22.55,
            "depth": 27.49
          },
          "warrantyInformation": "2 year warranty",
          "shippingInformation": "Ships in 1 week",
          "availabilityStatus": "In Stock",
          "reviews": [
            {
              "rating": 1,
              "comment": "Would not recommend!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Ava Harris",
              "reviewerEmail": "ava.harris@x.dummyjson.com"
            },
            {
              "rating": 2,
              "comment": "Very dissatisfied!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Liam Smith",
              "reviewerEmail": "liam.smith@x.dummyjson.com"
            },
            {
              "rating": 4,
              "comment": "Very pleased!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Christian Perez",
              "reviewerEmail": "christian.perez@x.dummyjson.com"
            }
          ],
          "returnPolicy": "7 days return policy",
          "minimumOrderQuantity": 8,
          "meta": {
            "createdAt": "2025-04-30T09:41:02.053Z",
            "updatedAt": "2025-04-30T09:41:02.053Z",
            "barcode": "7534096777716",
            "qrCode": "https://cdn.dummyjson.com/public/qr-code.png"
          },
          "images": [
            "https://cdn.dummyjson.com/product-images/kitchen-accessories/electric-stove/1.webp",
            "https://cdn.dummyjson.com/product-images/kitchen-accessories/electric-stove/2.webp",
            "https://cdn.dummyjson.com/product-images/kitchen-accessories/electric-stove/3.webp",
            "https://cdn.dummyjson.com/product-images/kitchen-accessories/electric-stove/4.webp"
          ],
          "thumbnail": "https://cdn.dummyjson.com/product-images/kitchen-accessories/electric-stove/thumbnail.webp"
        },
        {
          "id": 58,
          "title": "Fork",
          "description": "The Fork is a classic utensil for various dining and serving purposes. Its durable and ergonomic design makes it a reliable choice for everyday use.",
          "category": "kitchen-accessories",
          "price": 3.99,
          "discountPercentage": 8.07,
          "rating": 3.11,
          "stock": 7,
          "tags": [
            "kitchen tools",
            "utensils"
          ],
          "sku": "KIT-BRD-FOR-058",
          "weight": 9,
          "dimensions": {
            "width": 12.3,
            "height": 25.91,
            "depth": 22.57
          },
          "warrantyInformation": "No warranty",
          "shippingInformation": "Ships in 1 month",
          "availabilityStatus": "In Stock",
          "reviews": [
            {
              "rating": 5,
              "comment": "Highly impressed!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Elena Baker",
              "reviewerEmail": "elena.baker@x.dummyjson.com"
            },
            {
              "rating": 5,
              "comment": "Very satisfied!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Avery Scott",
              "reviewerEmail": "avery.scott@x.dummyjson.com"
            },
            {
              "rating": 2,
              "comment": "Would not buy again!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Mateo Perez",
              "reviewerEmail": "mateo.perez@x.dummyjson.com"
            }
          ],
          "returnPolicy": "No return policy",
          "minimumOrderQuantity": 36,
          "meta": {
            "createdAt": "2025-04-30T09:41:02.053Z",
            "updatedAt": "2025-04-30T09:41:02.053Z",
            "barcode": "2851192866410",
            "qrCode": "https://cdn.dummyjson.com/public/qr-code.png"
          },
          "images": [
            "https://cdn.dummyjson.com/product-images/kitchen-accessories/fork/1.webp"
          ],
          "thumbnail": "https://cdn.dummyjson.com/product-images/kitchen-accessories/fork/thumbnail.webp"
        },
        {
          "id": 59,
          "title": "Glass",
          "description": "The Glass is a versatile and elegant drinking vessel suitable for a variety of beverages. Its clear design allows you to enjoy the colors and textures of your drinks.",
          "category": "kitchen-accessories",
          "price": 4.99,
          "discountPercentage": 7.92,
          "rating": 4.02,
          "stock": 46,
          "tags": [
            "drinkware",
            "glasses"
          ],
          "sku": "KIT-BRD-GLA-059",
          "weight": 10,
          "dimensions": {
            "width": 20.23,
            "height": 24.56,
            "depth": 26.97
          },
          "warrantyInformation": "3 year warranty",
          "shippingInformation": "Ships overnight",
          "availabilityStatus": "In Stock",
          "reviews": [
            {
              "rating": 5,
              "comment": "Very happy with my purchase!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Jackson Evans",
              "reviewerEmail": "jackson.evans@x.dummyjson.com"
            },
            {
              "rating": 3,
              "comment": "Waste of money!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Nicholas Bailey",
              "reviewerEmail": "nicholas.bailey@x.dummyjson.com"
            },
            {
              "rating": 5,
              "comment": "Very satisfied!",
              "date": "2025-04-30T09:41:02.053Z",
              "reviewerName": "Nathan Reed",
              "reviewerEmail": "nathan.reed@x.dummyjson.com"
            }
          ],
          "returnPolicy": "7 days return policy",
          "minimumOrderQuantity": 17,
          "meta": {
            "createdAt": "2025-04-30T09:41:02.053Z",
            "updatedAt": "2025-04-30T09:41:02.053Z",
            "barcode": "4900880403720",
            "qrCode": "https://cdn.dummyjson.com/public/qr-code.png"
          },
          "images": [
            "https://cdn.dummyjson.com/product-images/kitchen-accessories/glass/1.webp"
          ],
          "thumbnail": "https://cdn.dummyjson.com/product-images/kitchen-accessories/glass/thumbnail.webp"
        }
      ],
      "total": 76,
      "skip": 13,
      "limit": 5
    }
""".trimIndent()
