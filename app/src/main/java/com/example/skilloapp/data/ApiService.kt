package com.example.skilloapp.data

import com.example.skilloapp.data.model.commit.CommitRequest
import com.example.skilloapp.data.model.commit.CommitResponse
import com.example.skilloapp.data.model.home.HomeResponse
import com.example.skilloapp.data.model.login.LoginRequest
import com.example.skilloapp.data.model.login.LoginResponse
import com.example.skilloapp.data.model.reserva.DiaDaSemana
import com.example.skilloapp.data.model.reserva.HorarioResponse
import com.example.skilloapp.data.model.reserva.ReservaResponse
import com.example.skilloapp.data.model.reserva.SalvarReservaResponse
import com.example.skilloapp.data.model.suporte.CreateTaskRequest
import com.example.skilloapp.data.model.suporte.SuporteRequest
import com.example.skilloapp.data.model.suporte.SuporteUpdateRequest
import com.example.skilloapp.data.model.suporte.TicketResponse
import com.example.skilloapp.data.model.tasks.TasksResponse
import com.example.skilloapp.data.model.turmas.TurmaResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {


    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    // home
    @GET("/home")
    fun getHomeData(): Call<HomeResponse>

    // suporte
    @GET("/suporte/meusTickets")
    fun getMeusTickets(): Call<List<TicketResponse>>

    @POST("/suporte")
    fun criarTicketSuporte(@Body suporteRequest: SuporteRequest): Call<TicketResponse>

    @PUT("/suporte/{id}")
    fun atualizarTicketSuporte(
        @Path("id") id: Int,
        @Body suporteUpdateRequest: SuporteUpdateRequest
    ): Call<TicketResponse>

    @DELETE("/suporte/{id}")
    fun deletarTicketSuporte(@Path("id") id: Int): Call<Void>

    // Turmas
    @GET("/minhasTurmas")
    fun getMinhasTurmas(): Call<List<TurmaResponse>>

    //Commit
    @GET("/minhasTurmas/{idTurma}/materias/{idMateria}/commits")
    fun getCommits(
        @Path("idTurma") idTurma: Int,
        @Path("idMateria") idMateria: Int,
        @Query("sort") sort: String
    ): Call<CommitResponse>

    @POST("/minhasTurmas/{idTurma}/materias/{idMateria}/commits")
    fun criarCommit(
        @Path("idTurma") idTurma: Int,
        @Path("idMateria") idMateria: Int,
        @Body commitRequest: CommitRequest
    ): Call<Void>

    @PUT("/minhasTurmas/{idTurma}/materias/{idMateria}/commits/{idCommit}")
    fun atualizarCommit(
        @Path("idTurma") idTurma: Int,
        @Path("idMateria") idMateria: Int,
        @Path("idCommit") idCommit: Int,
        @Body commitRequest: CommitRequest
    ): Call<Void>

    @DELETE("/minhasTurmas/{idTurma}/materias/{idMateria}/commits/{idCommit}")
    fun excluirCommit(
        @Path("idTurma") idTurma: Int,
        @Path("idMateria") idMateria: Int,
        @Path("idCommit") idCommit: Int
    ): Call<Void>

    // Tasks
    @GET("/minhasTasks")
    fun getTasks(): Call<List<TasksResponse>>

    @POST("/minhasTasks")
    fun criarTask(@Body createTaskRequest: CreateTaskRequest): Call<Void>

    @PUT("/minhasTasks/{id}")
    fun atualizarTask(
        @Path("id") id: Int,
        @Body updateTaskRequest: CreateTaskRequest
    ): Call<Void>

    @DELETE("/minhasTasks/{id}")
    fun excluirTask(@Path("id") id: Int): Call<Void>

    // Horario

    @GET("/meuHorario/{diaDaSemana}")
    fun getHorario(
        @Path("diaDaSemana") diaDaSemana: DiaDaSemana,
        @Query("sort") sortBy: String = "horario"
    ): Call<List<HorarioResponse>>

    // Reserva

    @GET("/meuHorario/{diaDaSemana}/{id}/reserva")
    fun getReserva(
        @Path("diaDaSemana") diaDaSemana: DiaDaSemana,
        @Path("id") id: Int
    ): Call<ReservaResponse>

    @POST("/meuHorario/{diaDaSemana}/{id}/reserva/{id}")
    fun salvarReserva(
        @Path("diaDaSemana") diaDaSemana: DiaDaSemana, // Use a enumeração DiaDaSemana aqui
        @Path("id") id: Int
    ): Call<SalvarReservaResponse>

    @DELETE("/meuHorario/{diaDaSemana}/{id}/reserva/{id}")
    fun removerReserva(
        @Path("diaDaSemana") diaDaSemana: DiaDaSemana, // Use a enumeração DiaDaSemana aqui
        @Path("id") id: Int
    ): Call<Void>


}
