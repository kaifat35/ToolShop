package com.efimov.toolshop.domain.usecase.cart

import com.efimov.toolshop.domain.repository.CartRepository
import javax.inject.Inject

class ClearAllUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(){
        return repository.clearAll()
    }
}