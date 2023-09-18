package com.example.skilloapp.data.model.home

import com.example.skilloapp.data.model.common.Materia
import com.example.skilloapp.data.model.common.Professor
import com.example.skilloapp.data.model.turmas.Turma

data class HomeResponse(
    val id: Int,
    val dia: String,
    val horario: String,
    val materia: Materia,
    val turma: Turma,
    val professores: List<Professor>
)