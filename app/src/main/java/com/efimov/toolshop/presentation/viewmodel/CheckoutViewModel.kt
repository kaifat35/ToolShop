package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efimov.toolshop.data.remove.CreateOrderRequest
import com.efimov.toolshop.data.remove.CreatePaymentRequest
import com.efimov.toolshop.data.remove.OrderItemRequest
import com.efimov.toolshop.domain.model.CartItem
import com.efimov.toolshop.domain.model.DeliveryMethod
import com.efimov.toolshop.domain.model.Payment
import com.efimov.toolshop.domain.model.PaymentMethod
import com.efimov.toolshop.domain.repository.CartRepository
import com.efimov.toolshop.domain.repository.UserRepository
import com.efimov.toolshop.domain.usecase.order.CreateOrderUseCase
import com.efimov.toolshop.domain.usecase.payment.CreatePaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val createOrderUseCase: CreateOrderUseCase,
    private val createPaymentUseCase: CreatePaymentUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    val cartItems: StateFlow<List<CartItem>> = cartRepository.getAllItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            cartItems.collectLatest { items ->
                _uiState.update { it.copy(totalAmount = items.sumOf { item -> item.totalPrice }) }
            }
        }
    }

    fun setDeliveryMethod(method: DeliveryMethod) {
        _uiState.update { it.copy(deliveryMethod = method) }
    }

    fun setAddress(address: String) {
        _uiState.update { it.copy(address = address) }
    }

    fun setComment(comment: String) {
        _uiState.update { it.copy(comment = comment) }
    }

    fun setPaymentMethod(method: PaymentMethod) {
        _uiState.update { it.copy(paymentMethod = method) }
    }

    fun placeOrder() {
        viewModelScope.launch {
            val currentUserId = userRepository.getUserId().first() ?: return@launch
            val items = cartItems.value.map {
                OrderItemRequest(
                    productId = it.product.id,
                    quantity = it.quantity,
                    startDate = it.startDate.toString(),
                    endDate = it.endDate.toString()
                )
            }

            val orderRequest = CreateOrderRequest(
                customerId = currentUserId,
                items = items,
                deliveryMethod = uiState.value.deliveryMethod.name,
                address = uiState.value.address,
                comment = uiState.value.comment,
                paymentMethod = uiState.value.paymentMethod.name
            )

            val order = createOrderUseCase(orderRequest)
            val payment = createPaymentUseCase(
                CreatePaymentRequest(
                    orderId = order.id,
                    amount = order.totalAmount,
                    method = uiState.value.paymentMethod.name
                )
            )

            cartRepository.clearAll()
            _uiState.update { it.copy(orderId = order.id, payment = payment) }
        }
    }
}

data class CheckoutUiState(
    val deliveryMethod: DeliveryMethod = DeliveryMethod.PICKUP,
    val address: String? = null,
    val comment: String? = null,
    val paymentMethod: PaymentMethod = PaymentMethod.SBP,
    val totalAmount: BigDecimal = BigDecimal.ZERO,
    val orderId: Int? = null,
    val payment: Payment? = null
) {
    val isValid: Boolean
        get() = when (deliveryMethod) {
            DeliveryMethod.PICKUP -> true
            DeliveryMethod.DELIVERY -> !address.isNullOrBlank()
        }
}