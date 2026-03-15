package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efimov.toolshop.domain.model.Payment
import com.efimov.toolshop.domain.model.PaymentMethod
import com.efimov.toolshop.domain.model.PaymentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun loadPayment(orderId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _uiState.update {
                it.copy(
                    isLoading = false,
                    payment = Payment(
                        id = orderId,
                        orderId = orderId,
                        amount = BigDecimal.ZERO,
                        method = PaymentMethod.SBP,
                        status = PaymentStatus.PENDING,
                        sbpQrCode = "toolshop-order-$orderId"
                    )
                )
            }
        }
    }

    fun checkPaymentStatus() {
        _uiState.update { state ->
            state.copy(payment = state.payment?.copy(status = PaymentStatus.SUCCESS))
        }
    }
}

data class PaymentUiState(
    val payment: Payment? = null,
    val isLoading: Boolean = false
)