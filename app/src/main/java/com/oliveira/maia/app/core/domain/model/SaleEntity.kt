package com.oliveira.maia.app.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oliveira.maia.app.core.utils.Constants.SALES_TABLE

@Entity(tableName = SALES_TABLE)
class SaleEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saleId")
    val saleId: Int = 0,
    @ColumnInfo(name = "nome_do_cliente")
    val clientName: String = "",
    @ColumnInfo(name = "sale")
    val sale: List<ProductEntity> = listOf(),
    @ColumnInfo(name = "valor_total_do_pedido")
    val totalAmount: Double = 0.0,
    @ColumnInfo(name = "data_do_pedido")
    val orderDate: String = "",
    @ColumnInfo(name = "quantidade_de_produtos_do_pedido")
    val productsQuantity: Int = 0,
)