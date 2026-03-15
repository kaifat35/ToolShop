package com.efimov.toolshop.data.local.mapper

import com.efimov.toolshop.data.local.entity.CartItemDbModel
import com.efimov.toolshop.data.local.entity.CategoryDbModel
import com.efimov.toolshop.domain.model.CartItem
import com.efimov.toolshop.domain.model.Category
import java.time.LocalDate

fun List<CartItemDbModel>.toEntities() : List<CartItem> {
    return map {
        CartItem(
            product = it.product,
            quantity = it.quantity,
            startDate = it.startDate as LocalDate?,
            endDate = it.endDate as LocalDate?,
            productId = it.productId,
            productJson = it.productJson
        )
    }.distinct()
}

fun CategoryDbModel.toEntity() : Category {
    return Category(id,name,parentId)
}