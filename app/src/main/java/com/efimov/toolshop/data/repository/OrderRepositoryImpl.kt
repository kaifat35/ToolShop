package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.remove.ApiService
import com.efimov.toolshop.data.remove.CreateOrderRequest
import com.efimov.toolshop.domain.model.Order
import com.efimov.toolshop.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: ApiService
) : OrderRepository {

    override suspend fun getOrders(customerId: Int): List<Order> {
        return api.getOrders(customerId)
    }

    override suspend fun createOrder(orderRequest: CreateOrderRequest): Order {
        return api.createOrder(orderRequest)
    }
}

