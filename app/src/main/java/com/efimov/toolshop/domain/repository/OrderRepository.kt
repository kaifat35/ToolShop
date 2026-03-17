package com.efimov.toolshop.domain.repository

import com.efimov.toolshop.data.remove.CreateOrderRequest
import com.efimov.toolshop.data.remove.CreateOrderResponse
import com.efimov.toolshop.domain.model.Order

interface OrderRepository {
    suspend fun getOrders(customerId: Int): List<Order>
    suspend fun createOrder(orderRequest: CreateOrderRequest): CreateOrderResponse
}