package com.coolcats.localinterestsapplication.model

data class ResultX(
    val address_components: List<AddressComponentX>,
    val formatted_address: String,
    val geometry: GeometryX,
    val place_id: String,
    val plus_code: PlusCodeXX,
    val types: List<String>
)