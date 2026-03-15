package com.efimov.toolshop.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.efimov.toolshop.domain.model.DeliveryMethod
import com.efimov.toolshop.domain.model.PaymentMethod
import com.efimov.toolshop.presentation.viewmodel.CheckoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()

    LaunchedEffect(uiState.orderId) {
        uiState.orderId?.let { navController.navigate("payment/$it") }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Оформление заказа") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Способ получения
            Text("Способ получения", style = MaterialTheme.typography.titleMedium)
            Row {
                RadioButton(
                    selected = uiState.deliveryMethod == DeliveryMethod.PICKUP,
                    onClick = { viewModel.setDeliveryMethod(DeliveryMethod.PICKUP) }
                )
                Text("Самовывоз")
                RadioButton(
                    selected = uiState.deliveryMethod == DeliveryMethod.DELIVERY,
                    onClick = { viewModel.setDeliveryMethod(DeliveryMethod.DELIVERY) }
                )
                Text("Доставка")
            }

            if (uiState.deliveryMethod == DeliveryMethod.DELIVERY) {
                OutlinedTextField(
                    value = uiState.address ?: "",
                    onValueChange = { viewModel.setAddress(it) },
                    label = { Text("Адрес доставки") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            OutlinedTextField(
                value = uiState.comment ?: "",
                onValueChange = { viewModel.setComment(it) },
                label = { Text("Комментарий (необязательно)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Способ оплаты
            Text("Способ оплаты", style = MaterialTheme.typography.titleMedium)
            Row {
                RadioButton(
                    selected = uiState.paymentMethod == PaymentMethod.SBP,
                    onClick = { viewModel.setPaymentMethod(PaymentMethod.SBP) }
                )
                Text("СБП")
                RadioButton(
                    selected = uiState.paymentMethod == PaymentMethod.CARD,
                    onClick = { viewModel.setPaymentMethod(PaymentMethod.CARD) }
                )
                Text("Банковская карта")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Состав заказа
            Text("Ваш заказ:", style = MaterialTheme.typography.titleMedium)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    Text("${item.product.name} x${item.quantity}: ${item.totalPrice} ₽")
                }
            }

            Divider()

            Text("Итого: ${uiState.totalAmount} ₽", fontWeight = FontWeight.Bold)

            Button(
                onClick = { viewModel.placeOrder() },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isValid
            ) {
                Text("Перейти к оплате")
            }
        }
    }
}