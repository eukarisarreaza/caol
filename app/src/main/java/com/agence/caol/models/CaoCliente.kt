package com.agence.caol.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CaoCliente(
    var co_cidade: String="",
    @PrimaryKey
    var co_cliente: String="",
    var co_complemento_status: String="",
    var co_ramo: String="",
    var co_status: String="",
    var ddd2: String="",
    var ds_cargo_contato: String="",
    var ds_complemento: String="",
    var ds_email: String="",
    var ds_endereco: String="",
    var ds_referencia: String="",
    var ds_site: String="",
    var no_bairro: String="",
    var no_contato: String="",
    var no_fantasia: String="",
    var no_pais: String="",
    var no_razao: String="",
    var nu_cep: String="",
    var nu_cnpj: String="",
    var nu_fax: String="",
    var nu_numero: String="",
    var nu_ramal: String="",
    var nu_telefone: String="",
    var telefone2: String="",
    var tp_cliente: String=""
)