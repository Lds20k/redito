package com.mobile.redito.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.senac.noteapp.models.AppDatabase
import br.senac.noteapp.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobile.redito.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityRegisterBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(this.binding.root)

        this.auth = Firebase.auth
        this.binding.editTextEmail.setText(intent.getStringExtra("email"))
        this.binding.editTextSenha.setText(intent.getStringExtra("password"))

        this.binding.buttonRegistrar.setOnClickListener{
            val email = this.binding.editTextEmail.text.toString()
            val password = this.binding.editTextSenha.text.toString()
            val repeatPassword = this.binding.editTextRepetirSenha.text.toString()

            if(password == repeatPassword) {
                this.auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT)
                                .show()
                            Thread {
                                inserirUsuarioNoBancoLocal(email, password)
                                val resultIntent = Intent()
                                resultIntent.putExtra("email", email)
                                resultIntent.putExtra("senha", password)
                                setResult(RESULT_OK, resultIntent)
                                finish()
                            }.start()
                        } else {
                            Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Coloque senhas iguais", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun inserirUsuarioNoBancoLocal(email: String, password: String){
        val databaseInstance = Room.databaseBuilder(this, AppDatabase::class.java, "AppDb")
            .build()
        val daoUsuario = databaseInstance.usuarioDao()

        val usuario = Usuario(null, email,password)
        daoUsuario.inserir(usuario)
    }
}