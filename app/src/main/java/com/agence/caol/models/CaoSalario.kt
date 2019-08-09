package com.agence.caol.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CaoSalario(
    var brut_salario: String,
    @PrimaryKey
    var co_usuario: String,
    var dt_alteracao: String,
    var liq_salario: String
)