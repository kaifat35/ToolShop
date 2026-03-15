package com.efimov.toolshop.domain.usecase.payment

import com.efimov.toolshop.data.remove.CreatePaymentRequest
import com.efimov.toolshop.domain.model.Payment
import com.efimov.toolshop.domain.repository.PaymentRepository
import javax.inject.Inject

class CreatePaymentUseCase @Inject constructor(
    private val repository: PaymentRepository
) {
    suspend operator fun invoke(request: CreatePaymentRequest): Payment {
        return repository.createPayment(request)
    }
}