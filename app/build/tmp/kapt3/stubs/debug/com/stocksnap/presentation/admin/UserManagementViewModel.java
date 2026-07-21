package com.stocksnap.presentation.admin;

import androidx.lifecycle.ViewModel;
import com.stocksnap.data.model.User;
import com.stocksnap.data.repository.AuthRepository;
import com.stocksnap.data.repository.ProductRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000fJ\u000e\u0010#\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000fJ\u000e\u0010$\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000fJ\u0006\u0010%\u001a\u00020!J\u000e\u0010&\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000fJ\u000e\u0010\'\u001a\u00020!2\u0006\u0010(\u001a\u00020\tJ\u000e\u0010)\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u000fJ \u0010*\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e*\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010(\u001a\u00020\tH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R#\u0010\u001c\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u000b0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u001d\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0013\u00a8\u0006+"}, d2 = {"Lcom/stocksnap/presentation/admin/UserManagementViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "authRepository", "Lcom/stocksnap/data/repository/AuthRepository;", "(Lcom/stocksnap/data/repository/ProductRepository;Lcom/stocksnap/data/repository/AuthRepository;)V", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_userArrivalsCount", "", "", "_users", "", "Lcom/stocksnap/data/model/User;", "approvedUsers", "Lkotlinx/coroutines/flow/StateFlow;", "getApprovedUsers", "()Lkotlinx/coroutines/flow/StateFlow;", "currentUser", "getCurrentUser", "disabledUsers", "getDisabledUsers", "pendingUsers", "getPendingUsers", "searchQuery", "getSearchQuery", "userArrivalsCount", "getUserArrivalsCount", "users", "getUsers", "approveUser", "", "user", "disableUser", "enableUser", "loadUsers", "rejectUser", "setSearchQuery", "query", "toggleUserActiveStatus", "filterByQuery", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class UserManagementViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.stocksnap.data.model.User>> _users = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.User>> users = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, java.lang.Integer>> _userArrivalsCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, java.lang.Integer>> userArrivalsCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.model.User> currentUser = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.User>> pendingUsers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.User>> approvedUsers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.User>> disabledUsers = null;
    
    @javax.inject.Inject()
    public UserManagementViewModel(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository, @org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.User>> getUsers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, java.lang.Integer>> getUserArrivalsCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.model.User> getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.User>> getPendingUsers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.User>> getApprovedUsers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.stocksnap.data.model.User>> getDisabledUsers() {
        return null;
    }
    
    public final void loadUsers() {
    }
    
    public final void setSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void approveUser(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.User user) {
    }
    
    public final void rejectUser(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.User user) {
    }
    
    public final void disableUser(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.User user) {
    }
    
    public final void enableUser(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.User user) {
    }
    
    public final void toggleUserActiveStatus(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.model.User user) {
    }
    
    private final java.util.List<com.stocksnap.data.model.User> filterByQuery(java.util.List<com.stocksnap.data.model.User> $this$filterByQuery, java.lang.String query) {
        return null;
    }
}