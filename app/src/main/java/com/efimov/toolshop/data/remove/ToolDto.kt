package com.efimov.toolshop.data.remove

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class LoginRequest(val email: String, val password: String)
@Serializable
data class RegisterRequest(val name: String, val email: String, val phone: String, val password: String, val role: String = "CUSTOMER")
@Serializable
data class AuthResponse(val token: String, val userId: Int, val role: String)

@Serializable
data class CreateOrderRequest(
    val customerId: Int,
    val items: List<OrderItemRequest>,
    val deliveryMethod: String,
    val address: String?,
    val comment: String?,
    val paymentMethod: String
)

@Serializable
data class OrderItemRequest(
    val productId: Int,
    val quantity: Int,
    val startDate: String, // ISO format: "2025-04-10"
    val endDate: String
)

@Serializable
data class CreatePaymentRequest(
    val orderId: Int,
    @Contextual val amount: BigDecimal,
    val method: String // "SBP" или "CARD"
)