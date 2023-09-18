package com.example.skilloapp.data.model.suporte

data class CreateTaskRequest(
    val titulo: String,
    val prazo: String,
    val anotacao: String,
    val status: String
)