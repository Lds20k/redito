package br.senac.noteapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.redito.models.Post

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var username: String? = null,
    var email: String,
    var senha: String,
    var idComunidade: String? = null,
    var ultimaTela: String? = null
)