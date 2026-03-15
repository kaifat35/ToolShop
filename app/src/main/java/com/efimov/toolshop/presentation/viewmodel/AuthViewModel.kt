package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efimov.toolshop.domain.usecase.user.SaveAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val saveAuthUseCase: SaveAuthUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun setEmail(value: String) = _uiState.update { it.copy(email = value) }
    fun setPassword(value: String) = _uiState.update { it.copy(password = value) }
    fun setName(value: String) = _uiState.update { it.copy(name = value) }
    fun setPhone(value: String) = _uiState.update { it.copy(phone = value) }
    fun switchMode() = _uiState.update { it.copy(isLogin = !it.isLogin, error = null) }

    fun submit() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val state = _uiState.value
            val valid = state.email.isNotBlank() && state.password.length >= 4 &&
                    (state.isLogin || (state.name.isNotBlank() && state.phone.isNotBlank()))

            if (!valid) {
                _uiState.update { it.copy(isLoading = false, error = "Проверьте заполнение полей") }
                return@launch
            }

            runCatching {
                val userId = state.email.hashCode().let { if (it == Int.MIN_VALUE) 0 else kotlin.math.abs(it) }
                val token = "local-token-$userId"
                saveAuthUseCase(token, userId)
            }.onSuccess {
                _uiState.update { it.copy(isLoading = false, isAuthorized = true) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false, error = "Не удалось выполнить вход") }
            }
        }
    }
}

data class AuthUiState(
    val isLogin: Boolean = true,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val phone: String = "",
    val isLoading: Boolean = false,
    val isAuthorized: Boolean = false,
    val error: String? = null
)