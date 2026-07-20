package com.stocksnap.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _navigationRoute = MutableSharedFlow<String>(replay = 1)
    val navigationRoute: SharedFlow<String> = _navigationRoute

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            // Exactly 1 second delay
            delay(1000)
            val user = authRepository.currentUser.value
            val route = when {
                user == null -> "login"
                user.approvalStatus == "PENDING" -> "pendingApproval"
                user.approvalStatus == "REJECTED" -> "rejectedScreen"
                user.approvalStatus == "APPROVED" && !user.active -> "disabledScreen"
                else -> "dashboard"
            }
            _navigationRoute.emit(route)
        }
    }
}
