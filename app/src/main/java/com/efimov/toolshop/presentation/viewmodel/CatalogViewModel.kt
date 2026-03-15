package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efimov.toolshop.domain.model.Category
import com.efimov.toolshop.domain.model.Product
import com.efimov.toolshop.domain.repository.CartRepository
import com.efimov.toolshop.domain.usecase.category.GetCategoriesUseCase
import com.efimov.toolshop.domain.usecase.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    val cartItemCount: StateFlow<Int> = cartRepository.getItemCount().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    private var loadProductsJob: Job? = null

    init {
        loadCategories()
        loadProducts()
    }

    fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
        loadProducts()
    }

    fun onCategorySelected(categoryId: Int?) {
        _uiState.update { it.copy(selectedCategoryId = categoryId) }
        loadProducts()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            runCatching { getCategoriesUseCase() }
                .onSuccess { categories ->
                    _uiState.update { it.copy(categories = categories, errorMessage = null) }
                }
                .onFailure {
                    _uiState.update { it.copy(errorMessage = "Не удалось загрузить категории") }
                }
        }
    }

    private fun loadProducts() {
        loadProductsJob?.cancel()
        loadProductsJob = viewModelScope.launch {
            val currentState = _uiState.value
            _uiState.update { it.copy(isLoading = true) }
            runCatching {
                getProductsUseCase(
                    categoryId = currentState.selectedCategoryId,
                    query = currentState.query.takeIf { it.isNotBlank() }
                )
            }.onSuccess { products ->
                _uiState.update {
                    it.copy(products = products, isLoading = false, errorMessage = null)
                }
            }.onFailure {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Не удалось загрузить товары")
                }
            }
        }
    }
}

data class CatalogUiState(
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: Int? = null,
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)