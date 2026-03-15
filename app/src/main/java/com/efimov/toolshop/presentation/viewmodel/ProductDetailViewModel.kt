package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efimov.toolshop.domain.model.AvailabilityPeriod
import com.efimov.toolshop.domain.model.CartItem
import com.efimov.toolshop.domain.model.Product
import com.efimov.toolshop.domain.repository.CartRepository
import com.efimov.toolshop.domain.usecase.product.GetProductAvailabilityUseCase
import com.efimov.toolshop.domain.usecase.product.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getAvailabilityUseCase: GetProductAvailabilityUseCase,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val product = getProductUseCase(productId)
            val availability = getAvailabilityUseCase(productId)
            _uiState.update {
                it.copy(
                    product = product,
                    availability = availability,
                    isLoading = false,
                    availableQuantity = product.quantity - availability.sumOf { period -> period.bookedQuantity }
                )
            }
        }
    }

    fun updateStartDate(date: LocalDate) {
        _uiState.update { it.copy(startDate = date) }
    }

    fun updateEndDate(date: LocalDate) {
        _uiState.update { it.copy(endDate = date) }
    }

    fun addToCart() {
        viewModelScope.launch {
            val cartItem = CartItem(
                productId = _uiState.value.product.id,
                productJson = _uiState.value.product.name,
                product = _uiState.value.product,
                quantity = 1,
                startDate = _uiState.value.startDate as java.time.LocalDate?,
                endDate = _uiState.value.endDate as java.time.LocalDate?,
            )
            cartRepository.addItem(cartItem)
        }
    }
}

data class ProductDetailUiState(
    val product: Product = Product(
        id = 0,
        ownerId = 0,
        name = "",
        description = null,
        pricePerDay = BigDecimal.ZERO,
        categoryId = 0,
        quantity = 0,
        image = emptyList(),
    ),
    val availability: List<AvailabilityPeriod> = emptyList(),
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val availableQuantity: Int = 0,
    val isLoading: Boolean = false
)