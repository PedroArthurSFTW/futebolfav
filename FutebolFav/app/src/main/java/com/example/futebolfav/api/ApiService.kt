package com.example.futebolfav.api

import com.example.futebolfav.models.Player
import com.example.futebolfav.models.Team
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("jogadores")
    suspend fun createPlayer(@Body player: Player): Player

    @PUT("times/{nome}/{sigla}")
    suspend fun addPlayerToTeam(@Path("nome") nome: String, @Path("sigla") sigla: String)

    @GET("jogadores")
    suspend fun getAllPlayers(): List<Player>

    @GET("jogadores/sigla/{sigla}")
    suspend fun getPlayerByTeam(@Path("sigla") sigla: String): List<Player>

    @POST("times")
    suspend fun createTeam(@Body team: Team): Team

    @PUT("times/remove/{sigla}/{nome}")
    suspend fun removePlayerFromTeam(@Path("nome") nome: String, @Path("sigla") sigla: String)

    @GET("times")
    suspend fun getAllTeams(): List<Team>
}