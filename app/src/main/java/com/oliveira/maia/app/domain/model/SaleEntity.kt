package com.oliveira.maia.app.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class SaleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "numero_do_pedido") val id: Long = 0,
    @ColumnInfo(name = "nome_do_pedido") val productName: String,
    @ColumnInfo(name = "descricao_do_pedido")  val description: String,
    @ColumnInfo(name = "quantidade_do_pedido") val quantity: Int,
    @ColumnInfo(name = "preco_unitario_do_pedido") val unitPrice: Double,
    @ColumnInfo(name = "valor_total_do_pedido") val totalAmount: Double,
    @ColumnInfo(name = "data_do_pedido") val orderDate: String
)
