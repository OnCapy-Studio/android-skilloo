package com.example.skilloapp.data.model.suporte

import com.example.skilloapp.data.model.common.Professor

data class TicketResponse(
    val id: Int,
    val titulo: String,
    val lab: String,
    val descricao: String,
    val status: String,
    val professor: Professor
)