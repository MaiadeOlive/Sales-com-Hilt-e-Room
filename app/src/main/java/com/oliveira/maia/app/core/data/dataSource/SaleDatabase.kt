package com.oliveira.maia.app.core.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oliveira.maia.app.core.domain.model.SaleEntity
import com.oliveira.maia.app.core.utils.Converters

@Database(entities = [SaleEntity::class], version = 1)
@TypeConverters(Converters::class)

abstract class SaleDatabase : RoomDatabase() {
    abstract fun saleDao(): SaleDao
}