package com.example.skilloapp.data.model.turmas

import com.example.skilloapp.data.model.common.Materia
import com.example.skilloapp.data.model.common.Professor

data class AulaDTO(
    val id: Int,
    val dia: String,
    val horario: String,
    val materia: Materia,
    val turma: Turma,
    val professores: List<Professor>
)