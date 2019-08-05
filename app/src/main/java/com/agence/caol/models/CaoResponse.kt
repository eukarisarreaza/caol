package com.agence.caol.models

import java.util.ArrayList


data class CaoResponse<A>(
    var RECORDS: List<A> = ArrayList()
)