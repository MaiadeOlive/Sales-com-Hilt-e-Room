package com.oliveira.maia.app.core.domain.useCase

import com.oliveira.maia.app.core.data.model.SaleEntity
import com.oliveira.maia.app.core.data.repository.SaleRepository
import javax.inject.Inject

class GetSaleUseCase @Inject constructor(private val saleRepository: SaleRepository) {
    operator fun invoke(saleId: Int): SaleEntity {
        return saleRepository.getSaleById(saleId)
    }
}