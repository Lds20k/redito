package br.senac.noteapp.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Usuario::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}