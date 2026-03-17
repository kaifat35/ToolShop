package com.efimov.toolshop.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.efimov.toolshop.presentation.viewmodel.OrderDetailViewModel
import kotlinx.datetime.daysUntil
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun OrderDetailScreen(
    orderId: String?,
    navController: NavController,
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    val order by viewModel.order.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Детали заказа #${orderId ?: "-"}") }) }) { paddingValues ->
        if (isLoading) {
            Box(Modifier.fillMaxSize()) { CircularProgressIndicator(Modifier.align(Alignment.Center)) }
        } else {
            order?.let { ord ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("Заказ №${ord.id}", style = MaterialTheme.typography.headlineSmall)
                    Text("Дата: ${ord.createdAt}")
                    Text("Статус: ${ord.status}")
                    Text("Способ получения: ${ord.deliveryMethod}")
                    if (ord.address != null) Text("Адрес: ${ord.address}")
                    if (ord.comment != null) Text("Комментарий: ${ord.comment}")

                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("Состав заказа:", style = MaterialTheme.typography.titleMedium)
                    ord.items.forEach { item ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(item.productName, fontWeight = FontWeight.Bold)
                                Text("${item.quantity} шт. x ${item.pricePerDay} ₽/сутки")
                                Text("Период: ${item.startDate} - ${item.endDate}")
                                val days = item.startDate.daysUntil(item.endDate).coerceAtLeast(1)
                                Text("Стоимость: ${item.pricePerDay * days.toBigDecimal() * item.quantity.toBigDecimal()} ₽")
                            }
                        }
                    }

                    Divider()
                    Text("Итого: ${ord.totalAmount} ₽", fontWeight = FontWeight.Bold)
                }
            } ?: Box(Modifier.fillMaxSize()) { Text("Заказ не найден", Modifier.align(Alignment.Center)) }
        }
    }
}