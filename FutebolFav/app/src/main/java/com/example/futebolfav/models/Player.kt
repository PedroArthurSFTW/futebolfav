package com.example.futebolfav.models

data class Player(
    val nome: String,
    val posicao: String,
    val idade: Int,
    var siglaTime: String? = null
)
