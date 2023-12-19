package com.oliveira.maia.app.data.dataSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.oliveira.maia.app.domain.model.SaleEntity

@Dao
interface SaleDao {
    @Insert
    fun insertSale(sale: SaleEntity)

    @Query("SELECT * FROM sales")
    fun getAllSales(): List<SaleEntity>

    @Delete
    fun delete(sale: SaleEntity)
}