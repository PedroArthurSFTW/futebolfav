package com.example.futebolfav.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.futebolfav.api.RetrofitInstance
import com.example.futebolfav.models.Player
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PlayerViewModel: ViewModel(){
    private val _players = mutableStateOf<List<Player>>(emptyList())
    val players: State<List<Player>> = _players

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _isLoading = mutableStateOf(false)

    init {
        getPlayers()
    }

    fun addPlayerToTeam(playerName: String, teamSigla: String) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.addPlayerToTeam(playerName, teamSigla)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("AddPlayerError", "HTTP Error: ${e.code()} - $errorBody")
                _errorMessage.value = errorBody
            }
        }
    }

    private fun getPlayers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val playerList = RetrofitInstance.api.getAllPlayers()
                _players.value = playerList
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun getNumberOfPlayer(): Int{
        return _players.value.size
    }

    fun getPlayersWithoutClub(): Int {
        return _players.value.count { it.time == null }
    }

    fun getOldestPlayer(): Player? {
        return _players.value.maxByOrNull { it.idade }
    }

    fun getYoungestPlayer(): Player? {
        return _players.value.minByOrNull { it.idade }
    }

    fun getMostCommonPosition(): Pair<String, Int> {
        return _players.value
            .groupBy { it.posicao }
            .maxByOrNull { it.value.size }
            ?.let { Pair(it.key, it.value.size) }
            ?: Pair("", 0)
    }
}
