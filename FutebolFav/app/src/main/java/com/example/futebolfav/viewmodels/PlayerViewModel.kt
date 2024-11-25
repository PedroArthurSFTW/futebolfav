package com.example.futebolfav.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.futebolfav.api.RetrofitInstance
import com.example.futebolfav.models.Player
import com.example.futebolfav.models.Team
import kotlinx.coroutines.launch

class PlayerViewModel: ViewModel(){
    private val _players = mutableStateOf<List<Player>>(emptyList())
    val players: State<List<Player>> = _players

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _isLoading = mutableStateOf(false)

    init {
        getPlayers()
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
}
