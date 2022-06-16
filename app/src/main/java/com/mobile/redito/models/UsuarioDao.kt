package br.senac.noteapp.models

import androidx.room.*

@Dao
interface UsuarioDao {

    @Insert
    fun inserir(usuario: Usuario)

    @Query(value = "SELECT * FROM Usuario")
    fun pegarTodos(): List<Usuario>

    @Query(value = "SELECT * FROM Usuario where email = :emailStr")
    fun pegarUsuarioPorEmail(emailStr: String): Usuario

    @Update
    fun atualizarUsuario(vararg users: Usuario)

}