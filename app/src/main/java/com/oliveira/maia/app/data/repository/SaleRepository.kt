package com.oliveira.maia.app.data.repository

import com.oliveira.maia.app.data.dataSource.SaleDao
import com.oliveira.maia.app.domain.model.SaleEntity
import javax.inject.Inject

class SaleRepository @Inject constructor(private val saleDao: SaleDao) {
    fun insertSale(sale: SaleEntity) {
        saleDao.insertSale(sale)
    }

    fun getAllSales(): List<SaleEntity> {
        return saleDao.getAllSales()
    }

}
