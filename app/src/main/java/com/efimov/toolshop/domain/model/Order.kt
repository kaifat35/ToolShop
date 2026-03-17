package com.efimov.toolshop.domain.model

import kotlinx.datetime.LocalDate
import java.math.BigDecimal
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Order @OptIn(ExperimentalTime::class) constructor(
    val id: String,
    val createdAt: Instant,
    val totalAmount: BigDecimal,
    val status: OrderStatus,
    val deliveryMethod: DeliveryMethod,
    val address: String?,
    val comment: String?,
    val items: List<OrderItem>
)

enum class OrderStatus { NEW, PAID, CONFIRMED, ISSUED, COMPLETED, CANCELLED }
enum class DeliveryMethod { PICKUP, DELIVERY }

data class OrderItem(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val pricePerDay: BigDecimal
)
