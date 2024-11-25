package com.example.futebolfav.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.futebolfav.api.RetrofitInstance
import com.example.futebolfav.models.Player
import com.example.futebolfav.models.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

fun registerPlayer(player: Player, onSuccess: ()-> Unit, onError: (String) -> Unit){
    CoroutineScope(Dispatchers.IO).launch{
        try {
            RetrofitInstance.api.createPlayer(player)
            onSuccess()
        }catch (e: Exception){
            onError(e.message ?: "Erro desconhecido")
        }
    }
}

@Composable
fun PlayerRegistrationForm() {
    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("")}

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
            onClick = {val player = Player(name,position,age.toInt())
                      registerPlayer(player = player,
                          onSuccess = { message = "Player registered successfully!" },
                          onError = { error -> println(message = error) })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register Player")
        }
    }
}

fun registerTeam(team: Team, onSuccess: ()-> Unit, onError: (String) -> Unit){
    CoroutineScope(Dispatchers.IO).launch{
        try {
            RetrofitInstance.api.createTeam(team)
            onSuccess()
        }catch (e: Exception){
            onError(e.message ?: "Erro desconhecido")
        }
    }
}
@Composable
fun TeamRegistrationForm() {
    var name by remember { mutableStateOf("") }
    var acronym by remember { mutableStateOf("") }
    var foundationYear by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("")}
    var jogadores: List<Player> = emptyList()
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
            onClick = { val team = Team(name,acronym,foundationYear.toInt(),jogadores)
                registerTeam(team = team,
                    onSuccess = { message = "Player registered successfully!" },
                    onError = { error -> println(message = error) }) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register Team")
        }
    }
}