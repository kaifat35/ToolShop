package com.efimov.toolshop.data.remove

import com.efimov.toolshop.domain.model.Category
import com.efimov.toolshop.domain.model.Order
import com.efimov.toolshop.domain.model.OrderItem
import com.efimov.toolshop.domain.model.Payment
import com.efimov.toolshop.domain.model.Product
import retrofit2.http.*

interface ApiService {
    // Аутентификация
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    // Категории
    @GET("categories")
    suspend fun getCategories(): List<Category>

    // Товары
    @GET("products")
    suspend fun getProducts(
        @Query("categoryId") categoryId: Int? = null,
        @Query("q") query: String? = null,
        @Query("_page") page: Int = 1,
        @Query("_limit") limit: Int = 20
    ): List<Product>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    // Занятые даты
    @GET("orderItems")
    suspend fun getOrderItemsByProduct(@Query("productId") productId: Int): List<OrderItem>

    // Заказы
    @GET("orders")
    suspend fun getOrders(@Query("customerId") customerId: Int): List<Order>

    @POST("orders")
    suspend fun createOrder(@Body order: CreateOrderRequest): Order

    // Платежи
    @POST("payments")
    suspend fun createPayment(@Body request: CreatePaymentRequest): Payment

    @GET("payments/{id}")
    suspend fun getPayment(@Path("id") id: Int): Payment

}