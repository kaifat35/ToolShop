package com.efimov.toolshop.domain.usecase.order

import com.efimov.toolshop.data.remove.CreateOrderRequest
import com.efimov.toolshop.domain.model.Order
import com.efimov.toolshop.domain.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(request: CreateOrderRequest): Order {
        return repository.createOrder(request)
    }
}