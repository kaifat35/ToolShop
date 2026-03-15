package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efimov.toolshop.domain.model.Payment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getPaymentUseCase: GetPaymentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun loadPayment(orderId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val payment = getPaymentUseCase(orderId)
            _uiState.update { it.copy(payment = payment, isLoading = false) }
        }
    }

    fun checkPaymentStatus() {
        viewModelScope.launch {
            val updatedPayment = getPaymentUseCase(uiState.value.payment!!.id)
            _uiState.update { it.copy(payment = updatedPayment) }
        }
    }
}

data class PaymentUiState(
    val payment: Payment? = null,
    val isLoading: Boolean = false
)