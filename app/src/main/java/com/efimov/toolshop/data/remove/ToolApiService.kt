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
        @Query("name_like") nameLike: String? = null,
        @Query("description_like") descriptionLike: String? = null,
    ): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductDto

    @GET("orderItems")
    suspend fun getOrderItemsByProduct(@Query("productId") productId: Int): List<OrderItem>

    @GET("orders")
    suspend fun getOrders(@Query("customerId") customerId: Int): List<Order>

    @POST("orders")
    suspend fun createOrder(@Body order: CreateOrderRequest): CreateOrderResponse

    @POST("payments")
    suspend fun createPayment(@Body request: CreatePaymentRequest): Payment

    @GET("payments/{id}")
    suspend fun getPayment(@Path("id") id: String): Payment

    @GET("payments")
    suspend fun getPaymentsByOrderId(@Query("orderId") orderId: String): List<Payment>
}