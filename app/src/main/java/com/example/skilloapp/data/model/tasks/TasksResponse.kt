package com.example.skilloapp.data.model.tasks

data class TasksResponse(
    val id: Int,
    val titulo: String,
    val prazo: String,
    val anotacao: String,
    val status: String
)