package com.example.trial_junior.feature_junior.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trial_junior.feature_junior.domain.model.UserItem
import com.example.trial_junior.feature_junior.domain.use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Define UI events for navigation and user feedback
sealed class UserUiEvent {
    object Back : UserUiEvent()
    object SaveProfile : UserUiEvent()
    data class ShowSnackbar(val message: String) : UserUiEvent()
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    // Flow to emit UI events
    private val _eventFlow = MutableSharedFlow<UserUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun registerUser(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val user = userUseCases.registerUser(firstName, lastName, email, password)
                _state.value = _state.value.copy(user = user, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
                _eventFlow.emit(UserUiEvent.ShowSnackbar("Registration failed: ${e.message}"))
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val token = userUseCases.loginUser(email, password)
                _state.value = _state.value.copy(token = token, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
                _eventFlow.emit(UserUiEvent.ShowSnackbar("Login failed: ${e.message}"))
            }
        }
    }

    fun loginAdmin(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val token = userUseCases.loginUser(email, password) // Reuse loginUser logic
                val user = userUseCases.getMyProfile() // Fetch user details to check role
                if (user?.role?.lowercase() == "admin") {
                    _state.value = _state.value.copy(token = token, user = user, isLoading = false)
                } else {
                    _state.value = _state.value.copy(error = "Access denied: Admin role required", isLoading = false)
                    _eventFlow.emit(UserUiEvent.ShowSnackbar("Access denied: Admin role required"))
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
                _eventFlow.emit(UserUiEvent.ShowSnackbar("Admin login failed: ${e.message}"))
            }
        }
    }

    fun getMyProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val user = userUseCases.getMyProfile()
                _state.value = _state.value.copy(user = user, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
                _eventFlow.emit(UserUiEvent.ShowSnackbar("Failed to fetch profile: ${e.message}"))
            }
        }
    }

    fun updateProfile(
        email: String,
        firstName: String?,
        lastName: String?,
        newEmail: String?,
        password: String?,
        profileImageUrl: String?,
        backgroundImageUrl: String?
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val user = userUseCases.updateProfile(email, firstName, lastName, newEmail, password, profileImageUrl, backgroundImageUrl)
                _state.value = _state.value.copy(user = user, isLoading = false)
                _eventFlow.emit(UserUiEvent.SaveProfile) // Emit event to navigate back after successful update
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
                _eventFlow.emit(UserUiEvent.ShowSnackbar("Failed to update profile: ${e.message}"))
            }
        }
    }

    // Method to manually set an error
    fun setError(error: String?) {
        _state.value = _state.value.copy(error = error, isLoading = false)
        viewModelScope.launch {
            error?.let { _eventFlow.emit(UserUiEvent.ShowSnackbar(it)) }
        }
    }

    // Method to handle back navigation
    fun onBack() {
        viewModelScope.launch {
            _eventFlow.emit(UserUiEvent.Back)
        }
    }

    // Method to reset the state
    fun resetState() {
        _state.value = UserState()
    }
}

data class UserState(
    val user: UserItem? = null,
    val token: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val role: String = "user" // Default role
)