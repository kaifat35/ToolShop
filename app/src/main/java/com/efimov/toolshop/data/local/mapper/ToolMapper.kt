package com.efimov.toolshop.data.local.mapper

import com.efimov.toolshop.data.local.entity.CartItemDbModel
import com.efimov.toolshop.data.local.entity.CategoryDbModel
import com.efimov.toolshop.domain.model.CartItem
import com.efimov.toolshop.domain.model.Category
import com.efimov.toolshop.domain.model.Product
import com.google.gson.Gson
import java.time.LocalDate

private val gson = Gson()

fun CartItem.toDbModel(): CartItemDbModel {
    return CartItemDbModel(
        productId = productId,
        productJson = if (productJson.isBlank()) gson.toJson(product) else productJson,
        quantity = quantity,
        startDate = startDate?.toString(),
        endDate = endDate?.toString()
    )
}

fun List<CartItemDbModel>.toEntities(): List<CartItem> {
    return map { it.toEntity() }
}

fun CartItemDbModel.toEntity(): CartItem {
    return CartItem(
        product = gson.fromJson(productJson, Product::class.java),
        productId = productId,
        productJson = productJson,
        quantity = quantity,
        startDate = startDate?.let(LocalDate::parse),
        endDate = endDate?.let(LocalDate::parse)
    )
}

fun CategoryDbModel.toEntity(): Category {
    return Category(id = id, name = name, parentId = parentId)
}