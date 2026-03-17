package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efimov.toolshop.domain.model.Payment
import com.efimov.toolshop.domain.model.PaymentStatus
import com.efimov.toolshop.domain.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun loadPayment(orderId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching {
                paymentRepository.getPaymentByOrderId(orderId)
                    ?: error("Платёж для заказа не найден")
            }.onSuccess { payment ->
                _uiState.update { it.copy(isLoading = false, payment = payment) }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Не удалось загрузить платёж"
                    )
                }
            }
        }
    }

    fun checkPaymentStatus() {
        val paymentId = _uiState.value.payment?.id ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching { paymentRepository.getPayment(paymentId) }
                .onSuccess { payment ->
                    _uiState.update { it.copy(isLoading = false, payment = payment) }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Не удалось обновить статус оплаты"
                        )
                    }
                }
        }
    }
}

data class PaymentUiState(
    val payment: Payment? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isSuccess: Boolean
        get() = payment?.status == PaymentStatus.SUCCESS
}