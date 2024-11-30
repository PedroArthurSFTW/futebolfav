package com.example.futebolfav.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                                    append("NOME: ")
                                }
                                withStyle(style = TextStyle(fontSize = 16.sp).toSpanStyle()) {
                                    append(team.nome)
                                }
                            })
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("SIGLA: ")
                                }
                                withStyle(style = TextStyle(fontSize = 16.sp).toSpanStyle()) {
                                    append(team.sigla)
                                }
                            })
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("ANO DE FUNDAÇÃO: ")
                                }
                                withStyle(style = TextStyle(fontSize = 16.sp).toSpanStyle()) {
                                    append("${team.fundacao}")
                                }
                            })
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("NÚMERO DE JOGADORES: ")
                                }
                                withStyle(style = TextStyle(fontSize = 16.sp).toSpanStyle()) {
                                    append("${team.jogadores?.size ?: 0}")
                                }
                            })

                            if (!team.jogadores.isNullOrEmpty()) {
                                Text(buildAnnotatedString {
                                    withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                        append("JOGADORES:")
                                    }
                                })
                                val teamPlayers = viewModel.getPlayersByTeam(team.sigla)
                                teamPlayers.forEach { player ->
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "• ",
                                            color = MaterialTheme.colorScheme.primary,
                                            style = TextStyle(fontSize = 16.sp)
                                        )
                                        Text(
                                            "${player.nome} - ${player.idade} anos - ${player.posicao}",
                                            style = TextStyle(fontSize = 16.sp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Text("Nenhum time encontrado")
        }
    }
}

@Composable
fun PlayersList(viewModel: PlayerViewModel = viewModel(), teamsViewModel: TeamsViewModel = viewModel()) {
    val players by viewModel.players
    val teams by teamsViewModel.team
    val errorMessage by viewModel.errorMessage
    var expandedPlayer by remember { mutableStateOf<Player?>(null) }

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
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("NOME: ")
                                }
                                withStyle(style = TextStyle(fontSize = 16.sp).toSpanStyle()) {
                                    append(player.nome)
                                }
                            })
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("POSIÇÃO: ")
                                }
                                withStyle(style = TextStyle(fontSize = 16.sp).toSpanStyle()) {
                                    append(player.posicao)
                                }
                            })
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("IDADE: ")
                                }
                                withStyle(style = TextStyle(fontSize = 16.sp).toSpanStyle()) {
                                    append("${player.idade}")
                                }
                            })
                            Text(buildAnnotatedString {
                                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                                    append("TIME: ")
                                }
                                withStyle(style = TextStyle(fontSize = 16.sp).toSpanStyle()) {
                                    append(player.time ?: "Nenhum \uD83D\uDEAB")
                                }
                            })
                            if (player.time == null) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.CenterHorizontally)
                                ) {
                                    Button(
                                        onClick = { expandedPlayer = player },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            contentColor = Color.White
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Adicionar a um time",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("ADICIONAR EM UM TIME", color = Color.White)
                                    }

                                    DropdownMenu(
                                        expanded = expandedPlayer == player,
                                        onDismissRequest = { expandedPlayer = null }
                                    ) {
                                        teams.forEach { team ->
                                            DropdownMenuItem(
                                                text = { Text(team.nome) },
                                                onClick = {
                                                    viewModel.addPlayerToTeam(player.nome, team.sigla)
                                                    teamsViewModel.getTeams()
                                                    expandedPlayer = null
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Text("Nenhum jogador encontrado")
        }
    }
}