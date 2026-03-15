package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@HiltViewModel
class OrderHistoryViewModel @Inject constructor() : ViewModel() {

    private val demoOrders = listOf(
        DemoOrderFactory.create(id = 1001, total = BigDecimal("3200.00")),
        DemoOrderFactory.create(id = 1002, total = BigDecimal("1500.00"))
    )

    private val _orders = MutableStateFlow(demoOrders)
    val orders: StateFlow<List<com.efimov.toolshop.domain.model.Order>> = _orders.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
}

@OptIn(ExperimentalTime::class)
internal object DemoOrderFactory {
    fun create(id: Int, total: BigDecimal): com.efimov.toolshop.domain.model.Order {
        return com.efimov.toolshop.domain.model.Order(
            id = id,
            createdAt = Clock.System.now(),
            totalAmount = total,
            status = com.efimov.toolshop.domain.model.OrderStatus.NEW,
            deliveryMethod = com.efimov.toolshop.domain.model.DeliveryMethod.PICKUP,
            address = null,
            comment = null,
            items = listOf(
                com.efimov.toolshop.domain.model.OrderItem(
                    productId = 1,
                    productName = "Перфоратор",
                    quantity = 1,
                    startDate = LocalDate.parse("2026-03-10"),
                    endDate = LocalDate.parse("2026-03-12"),
                    pricePerDay = BigDecimal("1600.00")
                )
            )
        )
    }
}