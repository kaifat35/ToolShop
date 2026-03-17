package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.remove.ApiService
import com.efimov.toolshop.data.remove.CreatePaymentRequest
import com.efimov.toolshop.domain.model.Payment
import com.efimov.toolshop.domain.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: ApiService
) : PaymentRepository {

    override suspend fun createPayment(request: CreatePaymentRequest): Payment {
        return api.createPayment(request)
    }

    override suspend fun getPayment(id: Int): Payment {
        return api.getPayment(id)
    }

    override suspend fun getPaymentByOrderId(orderId: Int): Payment? {
        return api.getPaymentsByOrderId(orderId).firstOrNull()
    }
}
