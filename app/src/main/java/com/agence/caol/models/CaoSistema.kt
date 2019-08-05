package com.agence.caol.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CaoSistema(
    var co_arquitetura: String="",
    var co_cliente: String="",
    var co_email: String="",
    @PrimaryKey
    var co_sistema: String="",
    var co_usuario: String="",
    var ddd_telefone_solic: String="",
    var ds_caracteristica: String="",
    var ds_requisito: String="",
    var ds_sistema_resumo: String="",
    var dt_entrega: String="",
    var dt_solicitacao: String="",
    var no_diretoria_solic: String="",
    var no_sistema: String="",
    var no_usuario_solic: String="",
    var nu_telefone_solic: String=""
)