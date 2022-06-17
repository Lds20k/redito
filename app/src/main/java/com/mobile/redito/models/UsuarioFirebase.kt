package com.mobile.redito.models

data class UsuarioFirebase (
    var username: String,
    var comunidades: List<Comunidade>? = null,
    val idComunidade: String? = null
)
