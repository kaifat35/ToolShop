package com.efimov.toolshop.domain.repository

import com.efimov.toolshop.domain.model.CreateOrderRequest


interface OrderRepository {

    suspend fun getOrder(orderId: Int)

    suspend fun createOrder(): CreateOrderRequest
}