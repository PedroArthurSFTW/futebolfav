package com.example.futebolfav.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.futebolfav.models.Player
import com.example.futebolfav.models.Team

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
                Text("Players")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { showPlayers = false },
                modifier = Modifier.weight(1f)
            ) {
                Text("Teams")
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
fun PlayersList() {
    // TODO: Replace with actual data from API
    val players = remember { listOf<Player>() }

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
                    Text(text = "Name: ${player.nome}")
                    Text(text = "Position: ${player.posicao}")
                    Text(text = "Age: ${player.idade}")
                    player.siglaTime?.let {
                        Text(text = "Team: $it")
                    }
                }
            }
        }
    }
}

@Composable
fun TeamsList() {
    // TODO: Replace with actual data from API
    val teams = remember { listOf<Team>() }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(teams) { team ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Name: ${team.nome}")
                    Text(text = "Acronym: ${team.sigla}")
                    Text(text = "Foundation Year: ${team.fundacao}")
                }
            }
        }
    }
}