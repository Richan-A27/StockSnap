package com.stocksnap.domain.usecase

import com.stocksnap.data.database.Product
import com.stocksnap.data.repository.ProductRepository
import javax.inject.Inject

class SaveProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product): Long {
        return repository.insertProduct(product)
    }
}
