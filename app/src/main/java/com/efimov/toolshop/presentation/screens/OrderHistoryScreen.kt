package com.efimov.toolshop.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.efimov.toolshop.domain.model.Order
import com.efimov.toolshop.domain.model.OrderStatus
import com.efimov.toolshop.presentation.viewmodel.OrderHistoryViewModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    navController: NavController,
    viewModel: OrderHistoryViewModel = hiltViewModel()
) {
    val orders by viewModel.orders.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Мои заказы") }) }) { paddingValues ->
        when {
            isLoading -> Box(Modifier.fillMaxSize()) { CircularProgressIndicator(Modifier.align(Alignment.Center)) }
            orders.isEmpty() -> Box(Modifier.fillMaxSize().padding(paddingValues)) { Text("Пока нет заказов", Modifier.align(Alignment.Center)) }
            else -> LazyColumn(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(orders) { order ->
                    OrderCard(order = order, onClick = { navController.navigate("order/${order.id}") })
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun OrderCard(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("Заказ №${order.id} от ${order.createdAt}")
            Text("Статус: ${order.status}", color = when (order.status) {
                OrderStatus.PAID -> Color.Green
                OrderStatus.CANCELLED -> Color.Red
                else -> Color.Gray
            })
            Text("Сумма: ${order.totalAmount} ₽")
            Text("Товаров: ${order.items.size}")
        }
    }
}