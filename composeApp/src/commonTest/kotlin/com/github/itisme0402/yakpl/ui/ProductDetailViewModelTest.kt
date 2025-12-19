package com.github.itisme0402.yakpl.ui

import com.github.itisme0402.yakpl.db.FavoriteEntity
import com.github.itisme0402.yakpl.db.FavoritesDao
import com.github.itisme0402.yakpl.di.domainModule
import com.github.itisme0402.yakpl.di.networkModule
import com.github.itisme0402.yakpl.di.uiModule
import com.github.itisme0402.yakpl.network.httpClientConfigurationBlock
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest : KoinTest {

    private val isFavoriteFlow = MutableSharedFlow<Boolean>()
    private val favoritesDao: FavoritesDao = mock(MockMode.autoUnit) {
        every { isFavorite(PRODUCT_ID) } returns isFavoriteFlow
        everySuspend { addFavorite(any()) } returns Unit
        everySuspend { removeFavoriteById(any()) } returns Unit
    }

    private lateinit var mockRequestHandler: MockRequestHandler

    @BeforeTest
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(
                uiModule,
                domainModule,
                networkModule,
                module {
                    single {
                        HttpClient(
                            MockEngine.create {
                                requestHandlers.add { request ->

                                    assertEquals("https://dummyjson.com/products/5", request.url.toString())

                                    respond(
                                        content = TEST_PRODUCT_JSON,
                                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                                    )
                                }

                                dispatcher = testDispatcher
                            },
                            httpClientConfigurationBlock(get())
                        )
                    }
                    single<FavoritesDao> {
                        favoritesDao
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
    fun `loads the product`() = runTest {
        mockRequestHandler = { request ->

            assertEquals("https://dummyjson.com/products/5", request.url.toString())

            respond(
                content = TEST_PRODUCT_JSON,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val sut = get<ProductDetailViewModel> { parametersOf(PRODUCT_ID) }
        advanceUntilIdle()

        assertEquals(TITLE, sut.product.value?.name)
        assertEquals(DESCRIPTION, sut.product.value?.description)
    }

    @Test
    fun `tracks and toggles favorite state`() = runTest {
        mockRequestHandler = { _ ->
            respond(
                content = TEST_PRODUCT_JSON,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val sut = get<ProductDetailViewModel> { parametersOf(PRODUCT_ID) }
        launch {
            sut.isFavorite.take(3).collect()
        }
        advanceUntilIdle()

        isFavoriteFlow.emit(true)
        advanceUntilIdle()
        assertEquals(true, sut.isFavorite.value)

        sut.toggleFavorite()
        advanceUntilIdle()
        verifySuspend { favoritesDao.removeFavoriteById(PRODUCT_ID) }

        isFavoriteFlow.emit(false)
        sut.toggleFavorite()
        advanceUntilIdle()
        verifySuspend { favoritesDao.addFavorite(FavoriteEntity(PRODUCT_ID, TITLE, DESCRIPTION)) }
    }
}

private const val PRODUCT_ID = "5"
private const val TITLE = "Red Nail Polish"
private const val DESCRIPTION = "The Red Nail Polish offers a rich and glossy red hue for vibrant and polished nails. With a quick-drying formula, it provides a salon-quality finish at home."

private val TEST_PRODUCT_JSON = """
    {
      "id": 5,
      "title": "Red Nail Polish",
      "description": "The Red Nail Polish offers a rich and glossy red hue for vibrant and polished nails. With a quick-drying formula, it provides a salon-quality finish at home.",
      "category": "beauty",
      "price": 8.99,
      "discountPercentage": 11.44,
      "rating": 4.32,
      "stock": 79,
      "tags": [
        "beauty",
        "nail polish"
      ],
      "brand": "Nail Couture",
      "sku": "BEA-NAI-NAI-005",
      "weight": 8,
      "dimensions": {
        "width": 21.63,
        "height": 16.48,
        "depth": 29.84
      },
      "warrantyInformation": "1 month warranty",
      "shippingInformation": "Ships overnight",
      "availabilityStatus": "In Stock",
      "reviews": [
        {
          "rating": 2,
          "comment": "Poor quality!",
          "date": "2025-04-30T09:41:02.053Z",
          "reviewerName": "Benjamin Wilson",
          "reviewerEmail": "benjamin.wilson@x.dummyjson.com"
        },
        {
          "rating": 5,
          "comment": "Great product!",
          "date": "2025-04-30T09:41:02.053Z",
          "reviewerName": "Liam Smith",
          "reviewerEmail": "liam.smith@x.dummyjson.com"
        },
        {
          "rating": 1,
          "comment": "Very unhappy with my purchase!",
          "date": "2025-04-30T09:41:02.053Z",
          "reviewerName": "Clara Berry",
          "reviewerEmail": "clara.berry@x.dummyjson.com"
        }
      ],
      "returnPolicy": "No return policy",
      "minimumOrderQuantity": 22,
      "meta": {
        "createdAt": "2025-04-30T09:41:02.053Z",
        "updatedAt": "2025-04-30T09:41:02.053Z",
        "barcode": "4063010628104",
        "qrCode": "https://cdn.dummyjson.com/public/qr-code.png"
      },
      "images": [
        "https://cdn.dummyjson.com/product-images/beauty/red-nail-polish/1.webp"
      ],
      "thumbnail": "https://cdn.dummyjson.com/product-images/beauty/red-nail-polish/thumbnail.webp"
    }
""".trimIndent()
