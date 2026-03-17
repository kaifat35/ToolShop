package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.efimov.toolshop.domain.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor() : ViewModel() {

    private val _order = MutableStateFlow(
        DemoOrderFactory.create(id = "1001", total = BigDecimal("3200.00"))
    )
    val order: StateFlow<Order?> = _order.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
}