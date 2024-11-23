package com.example.futebolfav.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatisticsScreen() {
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
                Text("Total Players: 0") // TODO: Replace with actual count
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total Teams: 0") // TODO: Replace with actual count
                Spacer(modifier = Modifier.height(8.dp))
                Text("Team with Most Players: N/A") // TODO: Replace with actual data
            }
        }
    }
}