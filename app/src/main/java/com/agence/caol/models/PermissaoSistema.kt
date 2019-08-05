package com.agence.caol.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PermissaoSistema(
    @PrimaryKey(autoGenerate = true)
    var id: Int= 0,
    var co_sistema: String="",
    var co_tipo_usuario: String="",
    var co_usuario: String="",
    var co_usuario_atualizacao: String="",
    var dt_atualizacao: String="",
    var in_ativo: String=""
)