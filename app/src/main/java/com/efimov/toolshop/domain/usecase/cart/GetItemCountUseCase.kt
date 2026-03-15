package com.efimov.toolshop.domain.usecase.cart

import com.efimov.toolshop.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemCountUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<Int>{
        return repository.getItemCount()
    }
}