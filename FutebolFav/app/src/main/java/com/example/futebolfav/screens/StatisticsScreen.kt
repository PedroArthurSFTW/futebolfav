package com.example.futebolfav.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.futebolfav.viewmodels.PlayerViewModel
import com.example.futebolfav.viewmodels.TeamsViewModel

@Composable
fun StatisticsScreen(
    playerViewModel: PlayerViewModel = viewModel(),
    teamsViewModel: TeamsViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Estatísticas de Clube",
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()

                val teamWithMost = teamsViewModel.getTeamWithMostPlayers()
                val teamWithLeast = teamsViewModel.getTeamWithLeastPlayers()
                val oldestTeam = teamsViewModel.getOldestTeam()
                val youngestTeam = teamsViewModel.getYoungestTeam()

                Text("Total de Times: ${teamsViewModel.getNumberOfTeams()}")
                Text("Times Com Mais Jogadores: ${teamWithMost.first?.nome ?: "N/A"} (${teamWithMost.second} jogadores)")
                Text("Times Com Menos Jogadores: ${teamWithLeast.first?.nome ?: "N/A"} (${teamWithLeast.second} jogadores)")
                Text("Time Mais Velho: ${oldestTeam?.nome ?: "N/A"} (${oldestTeam?.fundacao ?: "N/A"} anos)")
                Text("Time Mais Novo: ${youngestTeam?.nome ?: "N/A"} (${youngestTeam?.fundacao ?: "N/A"} anos)")
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Estatísticas dos Jogadores",
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()

                val oldestPlayer = playerViewModel.getOldestPlayer()
                val youngestPlayer = playerViewModel.getYoungestPlayer()
                val mostCommonPosition = playerViewModel.getMostCommonPosition()

                Text("Total de Jogadores: ${playerViewModel.getNumberOfPlayer()}")
                Text("Total de Jogadores Sem Clube: ${playerViewModel.getPlayersWithoutClub()}")
                Text("Jogador Mais Velho: ${oldestPlayer?.nome ?: "N/A"} (${oldestPlayer?.idade ?: "N/A"} anos)")
                Text("Jogador Mais Novo: ${youngestPlayer?.nome ?: "N/A"} (${youngestPlayer?.idade ?: "N/A"} anos)")
                Text("Posição Com Mais Jogadores: ${mostCommonPosition.first} (${mostCommonPosition.second} jogadores)")
            }
        }
    }
}