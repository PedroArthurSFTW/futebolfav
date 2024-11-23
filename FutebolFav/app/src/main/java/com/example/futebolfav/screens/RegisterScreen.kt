package com.example.futebolfav.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen() {
    var isPlayerRegistration by remember { mutableStateOf(true) }

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
                onClick = { isPlayerRegistration = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("Player")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { isPlayerRegistration = false },
                modifier = Modifier.weight(1f)
            ) {
                Text("Team")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isPlayerRegistration) {
            PlayerRegistrationForm()
        } else {
            TeamRegistrationForm()
        }
    }
}

@Composable
fun PlayerRegistrationForm() {
    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Player Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Position") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: Implement player registration */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register Player")
        }
    }
}

@Composable
fun TeamRegistrationForm() {
    var name by remember { mutableStateOf("") }
    var acronym by remember { mutableStateOf("") }
    var foundationYear by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Team Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = acronym,
            onValueChange = { acronym = it },
            label = { Text("Acronym") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = foundationYear,
            onValueChange = { foundationYear = it },
            label = { Text("Foundation Year") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: Implement team registration */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register Team")
        }
    }
}