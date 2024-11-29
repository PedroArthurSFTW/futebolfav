package com.example.futebolfav.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.futebolfav.api.RetrofitInstance
import com.example.futebolfav.models.Player
import com.example.futebolfav.models.Team
import com.example.futebolfav.viewmodels.PlayerViewModel
import com.example.futebolfav.viewmodels.TeamsViewModel
import kotlinx.coroutines.launch

@Composable
fun ListScreen() {
    var showPlayers by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { showPlayers = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("Jogadores")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { showPlayers = false },
                modifier = Modifier.weight(1f)
            ) {
                Text("Times")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showPlayers) {
            PlayersList()
        } else {
            TeamsList()
        }
    }
}

@Composable
fun TeamsList(viewModel: TeamsViewModel = viewModel()) {
    val teams by viewModel.team
    val errorMessage by viewModel.errorMessage

    Column(modifier = Modifier.padding(16.dp)) {
        errorMessage?.let {
            Text(text = "Erro: $it", color = MaterialTheme.colorScheme.error)
        }

        if (teams.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(teams) { team ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("Nome: ")
                                }
                                append(team.nome)
                            })
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("Sigla: ")
                                }
                                append(team.sigla)
                            })
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("Ano de Fundação: ")
                                }
                                append("${team.fundacao}")
                            })
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("Número de Jogadores: ")
                                }
                                append("${team.jogadores?.size ?: 0}")
                            })

                            if (!team.jogadores.isNullOrEmpty()) {
                                Text("Jogadores:", style = MaterialTheme.typography.titleMedium)
                                val teamPlayers = viewModel.getPlayersByTeam(team.sigla)
                                teamPlayers.forEach { player ->
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("• ", color = MaterialTheme.colorScheme.primary)
                                        Text("${player.nome} - ${player.idade} - ${player.posicao}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Text("No teams found")
        }
    }
}

@Composable
fun PlayersList(viewModel: PlayerViewModel = viewModel()) {
    val players by viewModel.players
    val errorMessage by viewModel.errorMessage

    Column(modifier = Modifier.padding(16.dp)) {
        errorMessage?.let {
            Text(text = "Erro: $it", color = MaterialTheme.colorScheme.error)
        }

        if (players.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(players) { player ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(text = "Nome: ${player.nome}")
                            Text(text = "Posição: ${player.posicao}")
                            Text(text = "Idade: ${player.idade}")
                            Text(text = "Time: ${player.time ?: "Nenhum \uD83D\uDEAB"}")
                        }
                    }
                }
            }
        }
    }
}