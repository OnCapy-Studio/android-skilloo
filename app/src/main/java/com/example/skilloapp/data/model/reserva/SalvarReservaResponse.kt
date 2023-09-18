package com.example.skilloapp.data.model.reserva

data class SalvarReservaResponse(
    val labId: Int,
    val nome: String,
    val status: String,
    val reserva: ReservaResponse?
)