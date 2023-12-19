package com.oliveira.maia.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oliveira.maia.app.data.dataSource.SaleDao
import com.oliveira.maia.app.domain.model.SaleEntity

@Database(entities = [SaleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun saleDao(): SaleDao
}
