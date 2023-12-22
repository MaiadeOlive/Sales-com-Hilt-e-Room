package com.oliveira.maia.app.core.data.dataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oliveira.maia.app.core.domain.model.SaleEntity
import com.oliveira.maia.app.core.utils.Constants.SALES_TABLE

@Dao
interface SaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSale(sale: SaleEntity)

    @Query("SELECT * FROM $SALES_TABLE ORDER BY saleId desc")
    fun getAllSales(): MutableList<SaleEntity>

    @Query("SELECT * FROM $SALES_TABLE WHERE saleId LIKE :id")
    fun getSaleById(id: Int): SaleEntity
}