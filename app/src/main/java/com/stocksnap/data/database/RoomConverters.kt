package com.stocksnap.data.database

import androidx.room.TypeConverter
import com.stocksnap.domain.model.ProductStatus

class RoomConverters {

    @TypeConverter
    fun fromProductStatus(value: ProductStatus): String = value.name

    @TypeConverter
    fun toProductStatus(value: String): ProductStatus = ProductStatus.valueOf(value)
}
