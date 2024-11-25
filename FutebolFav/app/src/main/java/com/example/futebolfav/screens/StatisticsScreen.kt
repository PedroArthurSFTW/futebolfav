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
fun StatisticsScreen(playerViewModel: PlayerViewModel = viewModel(), teamsViewModel: TeamsViewModel = viewModel()) {
    val numberOfPlayer = playerViewModel.getNumberOfPlayer()
    val numberOfTeams = teamsViewModel.getNumberOfTeams()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Total Players: $numberOfPlayer") // TODO: Replace with actual count
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total Teams: $numberOfTeams") // TODO: Replace with actual count
                Spacer(modifier = Modifier.height(8.dp))
                Text("Team with Most Players: 0") // TODO: Replace with actual data
            }
        }
    }
}