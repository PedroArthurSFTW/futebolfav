package com.example.futebolfav.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
                Text("Jogador")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { isPlayerRegistration = false },
                modifier = Modifier.weight(1f)
            ) {
                Text("Time")
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
    var expanded by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf(false) }
    var positionError by remember { mutableStateOf(false) }
    var ageError by remember { mutableStateOf(false) }


    val positions = listOf(
        "GOL", "ZAG", "LD", "LE", "ADE", "ADD", "VOL",
        "MC", "MEI", "ME", "MD", "PE", "SA", "ATA", "PD"
    )

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = it.trim().isEmpty()
            },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            isError = nameError,
            supportingText = {
                if (nameError) Text("Nome é obrigatório", color = MaterialTheme.colorScheme.error)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = position.ifEmpty { "Selecione a posição" },
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.ArrowDropDown, "Expand dropdown")
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            positions.forEach { positionOption ->
                DropdownMenuItem(
                    text = { Text(positionOption) },
                    onClick = {
                        position = positionOption
                        expanded = false
                    }
                )
            }
        }

        if (positionError) {
            Text(
                text = "Posição é obrigatória",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it
                ageError = it.trim().isEmpty()
            },
            label = { Text("Idade") },
            modifier = Modifier.fillMaxWidth(),
            isError = ageError,
            supportingText = {
                if (ageError) Text("Idade é obrigatória", color = MaterialTheme.colorScheme.error)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))



        Button(
            onClick = {

                nameError = name.trim().isEmpty()
                positionError = position.trim().isEmpty()
                ageError = age.trim().isEmpty() || age.toIntOrNull() == null

                if (!nameError && !positionError && !ageError) {
                    val player = Player(name, position, age.toInt())
                    registerPlayer(
                        player = player,
                        onSuccess = {
                            message = "Jogador cadastrado com sucesso!"

                            name = ""
                            position = ""
                            age = ""

                            nameError = false
                            positionError = false
                            ageError = false
                        },
                        onError = { error -> message = error }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CADASTRAR JOGADOR")
        }

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
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

    var nameError by remember { mutableStateOf(false) }
    var acronymError by remember { mutableStateOf(false) }
    var foundationYearError by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = it.trim().isEmpty()
            },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            isError = nameError,
            supportingText = {
                if (nameError) Text("Nome é obrigatório", color = MaterialTheme.colorScheme.error)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = acronym,
            onValueChange = {
                acronym = it.take(4).uppercase()
                acronymError = it.trim().isEmpty()
            },
            label = { Text("Sigla") },
            modifier = Modifier.fillMaxWidth(),
            isError = acronymError,
            supportingText = {
                if (acronymError) Text("Sigla é obrigatória", color = MaterialTheme.colorScheme.error)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = foundationYear,
            onValueChange = {
                foundationYear = it
                foundationYearError = it.trim().isEmpty() || it.toIntOrNull() == null
            },
            label = { Text("Ano de Fundação") },
            modifier = Modifier.fillMaxWidth(),
            isError = foundationYearError,
            supportingText = {
                if (foundationYearError) Text("Ano de Fundação é obrigatório", color = MaterialTheme.colorScheme.error)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                nameError = name.trim().isEmpty()
                acronymError = acronym.trim().isEmpty()
                foundationYearError = foundationYear.trim().isEmpty() || foundationYear.toIntOrNull() == null

                if (!nameError && !acronymError && !foundationYearError) {
                    val team = Team(
                        nome = name,
                        sigla = acronym,
                        fundacao = foundationYear.toInt(),
                        jogadores = emptyList()
                    )
                    registerTeam(
                        team = team,
                        onSuccess = {
                            message = "Time cadastrado com sucesso!"

                            name = ""
                            acronym = ""
                            foundationYear = ""

                            nameError = false
                            acronymError = false
                            foundationYearError = false
                        },
                        onError = { error -> message = error }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CADASTRAR TIME")
        }

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}