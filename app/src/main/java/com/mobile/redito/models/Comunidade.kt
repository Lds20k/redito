package com.mobile.redito.models

import com.google.firebase.database.Exclude

data class Comunidade (
    var id: String? = null,
    var nome: String? = null,
    var posts: ArrayList<Post>? = null
)