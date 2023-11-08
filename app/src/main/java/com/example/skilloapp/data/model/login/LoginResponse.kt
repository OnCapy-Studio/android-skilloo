package com.example.skilloapp.data.model.login

data class LoginResponse(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val token: String
)
