package com.efimov.toolshop.domain.usecase.product

import com.efimov.toolshop.domain.model.AvailabilityPeriod
import com.efimov.toolshop.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductAvailabilityUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): List<AvailabilityPeriod>{
        return repository.getProductAvailability(productId)
    }
}