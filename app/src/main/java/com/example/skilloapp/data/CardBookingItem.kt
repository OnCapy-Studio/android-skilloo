package com.example.skilloapp.data

import com.example.skilloapp.data.model.reserva.DiaDaSemana

class CardBookingItem(
    var time: String,
    var duration: String,
    var labName: String,
    var subject: String,
    var room: String,
    var id: Int,
    var diaDaSemana: DiaDaSemana
)
