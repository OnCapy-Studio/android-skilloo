package com.example.skilloapp.data.model.turmas

import com.example.skilloapp.data.model.common.Materia

data class TurmaResponse(
    val idTurma: Int,
    val nomeTurma: String,
    val materias: List<Materia>
)