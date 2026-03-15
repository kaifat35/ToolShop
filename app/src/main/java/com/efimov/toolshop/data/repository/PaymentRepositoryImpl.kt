package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.local.db.AppDao
import com.efimov.toolshop.domain.model.Payment
import com.efimov.toolshop.domain.repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val dao: AppDao
) : PaymentRepository {
    override suspend fun createPayment(): Payment {
       return dao.createPayment()
    }
}