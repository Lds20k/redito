package br.senac.noteapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var email: String,
    var senha: String,
    var ultimaTela: String? = null
)