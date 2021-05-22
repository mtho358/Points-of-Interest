package com.coolcats.localinterestsapplication.model

data class GeocodeResponse(
    val plus_code: PlusCodeX,
    val results: List<ResultX>,
    val status: String
)