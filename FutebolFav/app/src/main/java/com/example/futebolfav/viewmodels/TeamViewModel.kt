package com.example.futebolfav.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.futebolfav.api.RetrofitInstance
import com.example.futebolfav.models.Team
import kotlinx.coroutines.launch

class TeamsViewModel: ViewModel(){
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

    fun getTeamWithMostPlayers(): Pair<Team?, Int> {
        _teams.value.forEach { team ->
            println("Team: ${team.nome}, Players: ${team.jogadores?.size ?: 0}")
        }

        if (_teams.value.isEmpty()) {
            return Pair(null, 0)
        }

        return _teams.value
            .filter { it.jogadores?.isNotEmpty() == true }
            .maxByOrNull { it.jogadores?.size ?: 0 }
            ?.let { Pair(it, it.jogadores?.size ?: 0) }
            ?: Pair(null, 0)
    }

    fun getTeamWithLeastPlayers(): Pair<Team?, Int> {
        return _teams.value
            .minByOrNull { it.jogadores?.size ?: 0 }
            ?.let { Pair(it, it.jogadores?.size ?: 0) }
            ?: Pair(null, 0)
    }

    fun getOldestTeam(): Team? {
        return _teams.value.minByOrNull { it.fundacao }
    }

    fun getYoungestTeam(): Team? {
        return _teams.value.maxByOrNull { it.fundacao }
    }
}