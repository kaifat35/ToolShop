package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.remove.ApiService
import com.efimov.toolshop.domain.model.AvailabilityPeriod
import com.efimov.toolshop.domain.model.Product
import com.efimov.toolshop.domain.repository.ProductRepository
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ProductRepository {

    override suspend fun getProducts(categoryId: Int?, query: String?): List<Product> {
        val normalizedQuery = query?.trim()?.lowercase().orEmpty()

        return api.getProducts(
            categoryId = categoryId,
            nameLike = null
        )
            .filter { dto ->
                if (normalizedQuery.isBlank()) {
                    true
                } else {
                    dto.name.lowercase().contains(normalizedQuery) ||
                            dto.description?.lowercase()?.contains(normalizedQuery) == true
                }
            }
            .map { dto ->
                Product(
                    id = dto.id,
                    ownerId = dto.ownerId,
                    name = dto.name,
                    description = dto.description,
                    pricePerDay = dto.pricePerDay,
                    categoryId = dto.categoryId,
                    quantity = dto.quantity,
                    image = dto.image
                )
            }
    }

    override suspend fun getProduct(id: Int): Product {
        val dto = api.getProduct(id)
        return Product(
            id = dto.id,
            ownerId = dto.ownerId,
            name = dto.name,
            description = dto.description,
            pricePerDay = dto.pricePerDay,
            categoryId = dto.categoryId,
            quantity = dto.quantity,
            image = dto.image
        )
    }

    override suspend fun getProductAvailability(productId: Int): List<AvailabilityPeriod> {
        val items = api.getOrderItemsByProduct(productId)
        return items.map {
            AvailabilityPeriod(
                productId = it.productId,
                startDate = LocalDate.parse(it.startDate.toString()),
                endDate = LocalDate.parse(it.endDate.toString()),
                bookedQuantity = it.quantity
            )
        }
    }
}