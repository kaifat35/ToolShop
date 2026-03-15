package com.efimov.toolshop.data.remove

import com.efimov.toolshop.domain.model.Category
import com.efimov.toolshop.domain.model.Order
import com.efimov.toolshop.domain.model.OrderItem
import com.efimov.toolshop.domain.model.Payment
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("products")
    suspend fun getProducts(
        @Query("categoryId") categoryId: Int? = null,
        @Query("q") query: String? = null,
        @Query("_page") page: Int = 1,
        @Query("_limit") limit: Int = 20
    ): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductDto

    @GET("orderItems")
    suspend fun getOrderItemsByProduct(@Query("productId") productId: Int): List<OrderItem>

    @GET("orders")
    suspend fun getOrders(@Query("customerId") customerId: Int): List<Order>

    @POST("orders")
    suspend fun createOrder(@Body order: CreateOrderRequest): Order

    @POST("payments")
    suspend fun createPayment(@Body request: CreatePaymentRequest): Payment

    @GET("payments/{id}")
    suspend fun getPayment(@Path("id") id: Int): Payment
}