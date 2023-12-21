package com.oliveira.maia.app.core.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oliveira.maia.app.utils.Constants.PRODUCT_TABLE

@Entity(tableName = PRODUCT_TABLE)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saleId")
    val productId: Int = 0,
    @ColumnInfo(name = "nome_do_produto")
    val productName: String = "",
    @ColumnInfo(name = "descricao_do_pedido")
    val description: String = "",
    @ColumnInfo(name = "quantidade_do_produto")
    val quantity: Int = 0,
    @ColumnInfo(name = "preco_unitario_do_produto")
    val unitPrice: Double = 0.0,
    @ColumnInfo(name = "valor_total_do_pedido")
    val totalAmount: Double = 0.0
)
