package com.oliveira.maia.app.core.domain.useCase

import com.oliveira.maia.app.core.data.repository.SaleRepository
import com.oliveira.maia.app.core.domain.model.SaleEntity
import javax.inject.Inject

class CreateNewSaleUseCase @Inject constructor(private val saleRepository: SaleRepository)  {
    operator fun invoke(sale: SaleEntity) {
        saleRepository.insertSale(sale)
    }
}