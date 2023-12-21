package com.oliveira.maia.app.core.data.repository

import com.oliveira.maia.app.core.data.dataSource.SaleDao
import com.oliveira.maia.app.core.data.model.SaleEntity
import javax.inject.Inject

class SaleRepository @Inject constructor(private val saleDao: SaleDao) {

    fun insertSale(sale: SaleEntity) = saleDao.insertSale(sale)
    fun getAllSales() = saleDao.getAllSales()
    fun getSaleById(id: Int) = saleDao.getSaleById(id)
}
