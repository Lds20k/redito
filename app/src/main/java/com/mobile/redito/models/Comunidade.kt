package com.mobile.redito.models

data class Comunidade (
    var id: String? = null,
    var nome: String? = null,
    var posts: List<Post>? = null
)