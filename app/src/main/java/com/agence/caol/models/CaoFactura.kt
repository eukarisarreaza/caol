package com.agence.caol.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CaoFactura(
    var co_cliente: String="",
    @PrimaryKey
    var co_fatura: String="",
    var co_os: String="",
    var co_sistema: String="",
    var comissao_cn: String="",
    var corpo_nf: String="",
    var data_emissao: String="",
    var num_nf: String="",
    var total: String="",
    var total_imp_inc: String="",
    var valor: String=""
)