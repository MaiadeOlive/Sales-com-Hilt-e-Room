package com.oliveira.maia.app.domain.useCase

import com.oliveira.maia.app.data.repository.SaleRepository
import com.oliveira.maia.app.domain.model.SaleEntity
import javax.inject.Inject

class GetAllSalesUseCase @Inject constructor(private val saleRepository: SaleRepository)  {
    operator fun invoke(): List<SaleEntity> {
        return saleRepository.getAllSales()
    }
}
