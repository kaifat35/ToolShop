package com.efimov.toolshop.domain.repository

import com.efimov.toolshop.domain.model.AvailabilityPeriod
import com.efimov.toolshop.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(categoryId: Int?, query: String?): List<Product>

    suspend fun getProduct(id: Int): Product

    suspend fun getProductAvailability(productId: Int): List<AvailabilityPeriod>

}