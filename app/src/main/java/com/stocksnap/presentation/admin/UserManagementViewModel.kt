package com.stocksnap.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stocksnap.data.model.User
import com.stocksnap.data.repository.AuthRepository
import com.stocksnap.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserManagementViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _userArrivalsCount = MutableStateFlow<Map<String, Int>>(emptyMap())
    val userArrivalsCount: StateFlow<Map<String, Int>> = _userArrivalsCount

    val currentUser: StateFlow<User?> = authRepository.currentUser

    // Filtered lists by section
    val pendingUsers: StateFlow<List<User>> = combine(_searchQuery, _users) { query, userList ->
        userList.filter { it.approvalStatus == "PENDING" }.filterByQuery(query)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val approvedUsers: StateFlow<List<User>> = combine(_searchQuery, _users) { query, userList ->
        userList.filter { it.approvalStatus == "APPROVED" && it.active }.filterByQuery(query)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val disabledUsers: StateFlow<List<User>> = combine(_searchQuery, _users) { query, userList ->
        userList.filter {
            (it.approvalStatus == "APPROVED" && !it.active) || it.approvalStatus == "REJECTED"
        }.filterByQuery(query)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            val list = repository.getAllUsers()
            _users.value = list
            val counts = list.associate { it.uid to repository.getArrivalsCountByUser(it.uid) }
            _userArrivalsCount.value = counts
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun approveUser(user: User) {
        viewModelScope.launch {
            repository.approveUser(user.uid, user.name)
            loadUsers()
        }
    }

    fun rejectUser(user: User) {
        viewModelScope.launch {
            repository.rejectUser(user.uid, user.name)
            loadUsers()
        }
    }

    fun disableUser(user: User) {
        viewModelScope.launch {
            repository.disableUser(user.uid, user.name)
            loadUsers()
        }
    }

    fun enableUser(user: User) {
        viewModelScope.launch {
            repository.enableUser(user.uid, user.name)
            loadUsers()
        }
    }

    fun toggleUserActiveStatus(user: User) {
        viewModelScope.launch {
            repository.updateUserActiveStatus(user.uid, user.name, !user.active)
            loadUsers()
        }
    }

    private fun List<User>.filterByQuery(query: String): List<User> {
        if (query.isBlank()) return this
        return filter {
            it.name.contains(query, ignoreCase = true) ||
            it.email.contains(query, ignoreCase = true)
        }
    }
}
