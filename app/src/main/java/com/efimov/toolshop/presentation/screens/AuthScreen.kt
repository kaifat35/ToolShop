package com.efimov.toolshop.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.efimov.toolshop.presentation.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isAuthorized) {
        if (uiState.isAuthorized) {
            navController.navigate("catalog") {
                popUpTo("auth") { inclusive = true }
            }
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Авторизация") }) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (!uiState.isLogin) {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::setName,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Имя") }
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = uiState.phone,
                    onValueChange = viewModel::setPhone,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Телефон") }
                )
                Spacer(Modifier.height(8.dp))
            }

            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::setEmail,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email") }
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::setPassword,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Пароль") }
            )

            uiState.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it)
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = viewModel::submit, modifier = Modifier.fillMaxWidth(), enabled = !uiState.isLoading) {
                if (uiState.isLoading) CircularProgressIndicator()
                else Text(if (uiState.isLogin) "Войти" else "Зарегистрироваться")
            }
            TextButton(onClick = viewModel::switchMode, modifier = Modifier.fillMaxWidth()) {
                Text(if (uiState.isLogin) "Нет аккаунта? Регистрация" else "Уже есть аккаунт? Войти")
            }
        }
    }
}