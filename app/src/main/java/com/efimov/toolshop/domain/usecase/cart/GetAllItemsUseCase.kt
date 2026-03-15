package com.efimov.toolshop.domain.usecase.cart

import com.efimov.toolshop.domain.model.CartItem
import com.efimov.toolshop.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>> {
        return repository.getAllItems()
    }
}