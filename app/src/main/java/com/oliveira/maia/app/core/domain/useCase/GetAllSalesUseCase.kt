package com.oliveira.maia.app.core.domain.useCase

import com.oliveira.maia.app.core.data.repository.SaleRepository
import com.oliveira.maia.app.core.data.model.SaleEntity
import javax.inject.Inject

class GetAllSalesUseCase @Inject constructor(private val saleRepository: SaleRepository)  {
    operator fun invoke(): List<SaleEntity> {
        val sales = mutableListOf<SaleEntity>()
        sales.addAll(saleRepository.getAllSales())
        return sales
    }
}
