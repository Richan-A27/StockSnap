package com.stocksnap.data.repository;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.stocksnap.data.model.User;
import kotlinx.coroutines.flow.StateFlow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0007\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\tJ$\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u000b2\u0006\u0010\f\u001a\u00020\rH\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u000bH\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\tR\u001a\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0012"}, d2 = {"Lcom/stocksnap/data/repository/AuthRepository;", "", "currentUser", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/stocksnap/data/model/User;", "getCurrentUser", "()Lkotlinx/coroutines/flow/StateFlow;", "refreshCurrentUser", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithGoogle", "Lkotlin/Result;", "account", "Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;", "signInWithGoogle-gIAlu-s", "(Lcom/google/android/gms/auth/api/signin/GoogleSignInAccount;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signOut", "signOut-IoAF18A", "app_release"})
public abstract interface AuthRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.StateFlow<com.stocksnap.data.model.User> getCurrentUser();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object refreshCurrentUser(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}