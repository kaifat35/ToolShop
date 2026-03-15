package com.efimov.toolshop.data.remove

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class LoginRequest(val email: String, val password: String)

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String = "CUSTOMER"
)

data class AuthResponse(val token: String, val userId: Int, val role: String)

data class CreateOrderRequest(
    val customerId: Int,
    val items: List<OrderItemRequest>,
    val deliveryMethod: String,
    val address: String?,
    val comment: String?,
    val paymentMethod: String
)

data class OrderItemRequest(
    val productId: Int,
    val quantity: Int,
    val startDate: String,
    val endDate: String
)

data class CreatePaymentRequest(
    val orderId: Int,
    val amount: BigDecimal,
    val method: String
)

data class ProductDto(
    val id: Int,
    val ownerId: Int,
    val name: String,
    val description: String?,
    val pricePerDay: BigDecimal,
    val categoryId: Int,
    val quantity: Int,
    @SerializedName("images") val image: List<String>
)