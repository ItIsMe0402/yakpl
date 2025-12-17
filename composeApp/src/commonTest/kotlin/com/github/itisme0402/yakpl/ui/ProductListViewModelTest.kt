package com.github.itisme0402.yakpl.ui

import com.github.itisme0402.yakpl.domain.GetProductsUseCase
import com.github.itisme0402.yakpl.model.Product
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    private val getProductsUseCase: GetProductsUseCase = mock()

    private lateinit var sut: ProductListViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initially loads unfiltered list`() = runTest {
        val products = List(7) {
            Product(
                id = it.toString(),
                name = "Product $it",
                description = "This is product $it"
            )
        }

        everySuspend {
            getProductsUseCase.getProducts("")
        } returns products

        sut = ProductListViewModel(
            getProductsUseCase,
        )
        advanceUntilIdle()

        assertEquals(products, sut.products.value)
    }
}
