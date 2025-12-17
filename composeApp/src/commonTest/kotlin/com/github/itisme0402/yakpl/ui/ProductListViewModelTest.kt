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
            getProductsUseCase.getProducts(query = "", skip = 0, limit = 20)
        } returns products

        sut = ProductListViewModel(
            getProductsUseCase,
        )
        advanceUntilIdle()

        assertEquals(products, sut.products.value)
    }

    @Test
    fun `loadMore appends products`() = runTest {
        val initialProducts = List(20) { Product(id = "$it", name = "Product $it", description = "Desc $it") }
        val moreProducts = List(5) { Product(id = "${20+it}", name = "Product ${20+it}", description = "Desc ${20+it}") }

        everySuspend {
            getProductsUseCase.getProducts("", 0, 20)
        } returns initialProducts

        everySuspend {
            getProductsUseCase.getProducts("", 20, 20)
        } returns moreProducts

        sut = ProductListViewModel(getProductsUseCase)
        advanceUntilIdle()

        assertEquals(initialProducts, sut.products.value)

        sut.loadMore()
        advanceUntilIdle()

        assertEquals(initialProducts + moreProducts, sut.products.value)
    }

    @Test
    fun `search query change resets list`() = runTest {
        val initialProducts = listOf(Product("1", "A", "A"))
        val searchedProducts = listOf(Product("2", "B", "B"))

        everySuspend { getProductsUseCase.getProducts("", 0, 20) } returns initialProducts
        everySuspend { getProductsUseCase.getProducts("query", 0, 20) } returns searchedProducts

        sut = ProductListViewModel(getProductsUseCase)
        advanceUntilIdle()
        
        assertEquals(initialProducts, sut.products.value)

        sut.onSearchQueryChanged("query")
        advanceUntilIdle()

        assertEquals(searchedProducts, sut.products.value)
    }
}
