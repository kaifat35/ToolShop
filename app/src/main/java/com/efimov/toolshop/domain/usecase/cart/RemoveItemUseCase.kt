package com.efimov.toolshop.domain.usecase.cart

import com.efimov.toolshop.domain.model.CartItem
import com.efimov.toolshop.domain.repository.CartRepository
import javax.inject.Inject

class RemoveItemUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(item: CartItem){
        return repository.removeItem(item)
    }
}