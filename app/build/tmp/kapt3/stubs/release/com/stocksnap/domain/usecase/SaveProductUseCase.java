package com.stocksnap.domain.usecase;

import com.stocksnap.data.database.Product;
import com.stocksnap.data.repository.ProductRepository;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086B\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/stocksnap/domain/usecase/SaveProductUseCase;", "", "repository", "Lcom/stocksnap/data/repository/ProductRepository;", "(Lcom/stocksnap/data/repository/ProductRepository;)V", "invoke", "", "product", "Lcom/stocksnap/data/database/Product;", "(Lcom/stocksnap/data/database/Product;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class SaveProductUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.stocksnap.data.repository.ProductRepository repository = null;
    
    @javax.inject.Inject()
    public SaveProductUseCase(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.repository.ProductRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object invoke(@org.jetbrains.annotations.NotNull()
    com.stocksnap.data.database.Product product, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
}