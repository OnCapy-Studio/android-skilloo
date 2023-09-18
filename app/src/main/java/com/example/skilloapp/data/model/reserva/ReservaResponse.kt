package com.example.skilloapp.data.model.reserva

data class ReservaResponse(
    val labId: Int,
    val nome: String,
    val status: String,
    val reserva: Reserva?
)