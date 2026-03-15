package com.efimov.toolshop.domain.usecase.order

import com.efimov.toolshop.domain.model.CreateOrderRequest
import com.efimov.toolshop.domain.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(): CreateOrderRequest {
        return repository.createOrder()
    }
}