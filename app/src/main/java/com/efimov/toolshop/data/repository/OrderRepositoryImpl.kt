package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.local.db.AppDao
import com.efimov.toolshop.domain.model.CreateOrderRequest
import com.efimov.toolshop.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepositoryImpl@Inject constructor(
    private val dao: AppDao
) : OrderRepository {
    override suspend fun getOrder(orderId: Int): Flow<Int> {
        return dao.getOrder(orderId)
    }

    override suspend fun createOrder(orderRequest: CreateOrderRequest) {
        return dao.createOrder(orderRequest)
    }
}