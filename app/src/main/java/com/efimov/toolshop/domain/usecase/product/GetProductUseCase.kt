package com.efimov.toolshop.domain.usecase.product

import com.efimov.toolshop.domain.model.Product
import com.efimov.toolshop.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Product {
        return repository.getProduct(id)
    }
}