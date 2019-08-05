package com.agence.caol.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CaoOs(
    var co_arquitetura: String="",
    var co_email: String="",
    @PrimaryKey
    var co_os: String="",
    var co_os_prospect_rel: String="",
    var co_sistema: String="",
    var co_status: String="",
    var co_usuario: String="",
    var ddd_tel_sol: String="",
    var ddd_tel_sol2: String="",
    var diretoria_sol: String="",
    var ds_caracteristica: String="",
    var ds_os: String="",
    var ds_requisito: String="",
    var dt_fim: String="",
    var dt_garantia: String="",
    var dt_imp: String="",
    var dt_inicio: String="",
    var dt_sol: String="",
    var nu_os: String="",
    var nu_tel_sol: String="",
    var nu_tel_sol2: String="",
    var usuario_sol: String=""
)