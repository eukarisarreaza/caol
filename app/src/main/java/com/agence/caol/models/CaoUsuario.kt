package com.agence.caol.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CaoUsuario(
    @PrimaryKey
    var co_usuario: String="",
    var co_usuario_autorizacao: String="",
    var ds_bairro: String="",
    var ds_comp_end: String="",
    var ds_endereco: String="",
    var ds_senha: String="",
    var dt_admissao_empresa: String="",
    var dt_alteracao: String="",
    var dt_desligamento: String="",
    var dt_expedicao: String="",
    var dt_expiracao: String="",
    var dt_inclusao: String="",
    var dt_nascimento: String="",
    var icq: String="",
    var instant_messenger: String="",
    var msn: String="",
    var no_cidade: String="",
    var no_email: String="",
    var no_email_pessoal: String="",
    var no_orgao_emissor: String="",
    var no_usuario: String="",
    var nu_cep: String="",
    var nu_cpf: String="",
    var nu_matricula: String="",
    var nu_rg: String="",
    var nu_telefone: String="",
    var uf_cidade: String="",
    var uf_orgao_emissor: String="",
    var url_foto: String="",
    var yms: String="",



    var selected: Boolean= false
)