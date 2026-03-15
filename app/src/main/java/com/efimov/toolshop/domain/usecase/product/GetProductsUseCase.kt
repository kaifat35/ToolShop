package com.efimov.toolshop.domain.usecase.product

import com.efimov.toolshop.domain.model.Product
import com.efimov.toolshop.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(categoryId: Int?, query: String?): List<Product>{
        return repository.getProducts(categoryId,query)
    }
}