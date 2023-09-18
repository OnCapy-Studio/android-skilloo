package com.example.skilloapp.data.model.reserva

data class Reserva(
    val labId: Int,
    val nome: String,
    val status: String,
    val reserva: ReservaInfo?
)