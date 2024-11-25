package com.example.futebolfav.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.futebolfav.api.RetrofitInstance
import com.example.futebolfav.models.Team
import kotlinx.coroutines.launch

class TeamsViewModel: ViewModel(){
    val clubMostPlayers = mutableStateOf("")
    private val _teams = mutableStateOf<List<Team>>(emptyList())
    val team: State<List<Team>> = _teams

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _isLoading = mutableStateOf(false)

    init {
        getTeams()
    }

    private fun getTeams() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val teamList = RetrofitInstance.api.getAllTeams()
                _teams.value = teamList
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getNumberOfTeams(): Int{
        return _teams.value.size
    }

    fun teamWhitMostPlayers(){
        // Encontrar o time com mais jogadores
        val mostPlayers = _teams.value.reduce { most, team ->
            if (team.jogadores.size > most.jogadores.size) team else most
        }

        // Filtrar e criar uma string com times que têm o mesmo número de jogadores
        clubMostPlayers.value = _teams.value
            .filter { team -> team.jogadores.size == mostPlayers.jogadores.size }
            .map { team -> "${team.nome} (${team.jogadores.size})" }
            .joinToString(" | ")
    }
}