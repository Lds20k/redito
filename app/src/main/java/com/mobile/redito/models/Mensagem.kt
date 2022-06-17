package com.mobile.redito.models

data class Mensagem(
    val nomeUsuario: String,
    val dataHora: String,
    val mensagem: String,
    val curtidas: Int
)
