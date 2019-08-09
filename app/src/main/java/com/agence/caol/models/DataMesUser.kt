package com.agence.caol.models

data class DataMesUser (
    var caoUsuario: CaoUsuario,
    var list_data: List<DataMes>,
    var total_liquido: Double,
    var total_fijo: Double,
    var total_comision: Double,
    var total_lucro: Double
    )