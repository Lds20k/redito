package com.mobile.redito.models

data class Post(
    var id: String? = null,
    var idComunidade: String? = null,
    var idDono: String = "",
    var nomeDono: String = "",
    var conteudo: String = "",
    var hora: String = "",
    var likes: Int = 0,
    var comentarios: List<String>? = null,
)