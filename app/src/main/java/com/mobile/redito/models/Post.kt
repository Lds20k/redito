package com.mobile.redito.models

data class Post(
    var id: String? = null,
    var conteudo: String,
    var hora: String,
    var likes: Int,
    var comentarios: List<String>? = null
)