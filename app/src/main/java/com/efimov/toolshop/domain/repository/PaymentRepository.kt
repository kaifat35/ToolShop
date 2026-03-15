package com.efimov.toolshop.domain.repository

import com.efimov.toolshop.domain.model.Payment

interface PaymentRepository {

    suspend fun createPayment(): Payment


}