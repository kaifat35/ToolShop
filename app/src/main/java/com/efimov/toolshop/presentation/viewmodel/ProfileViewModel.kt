package com.efimov.toolshop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.efimov.toolshop.domain.usecase.user.ClearAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearAuthUseCase: ClearAuthUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            name = "Пользователь",
            email = "user@toolshop.local",
            phone = "+7 900 000-00-00"
        )
    )
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            clearAuthUseCase()
            _uiState.update { it.copy(isAuthorized = false) }
        }
    }
}

data class ProfileUiState(
    val name: String,
    val email: String,
    val phone: String,
    val isAuthorized: Boolean = true
)