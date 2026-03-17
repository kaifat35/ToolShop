package com.efimov.toolshop.domain.repository

import com.efimov.toolshop.data.remove.CreatePaymentRequest
import com.efimov.toolshop.domain.model.Payment

interface PaymentRepository {
    suspend fun createPayment(request: CreatePaymentRequest): Payment
    suspend fun getPayment(id: Int): Payment
    suspend fun getPaymentByOrderId(orderId: Int): Payment?
}
