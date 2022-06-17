package com.mobile.redito.activities

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.senac.noteapp.models.AppDatabase
import br.senac.noteapp.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.mobile.redito.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if(currentUser != null){
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
              auth.signOut()
        } else {
            Thread{
                colocarUsernameESenha()
            }.start()
        }

        this.binding.buttonRegistrar.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("email", this.binding.editTextEmail.text.toString())
            intent.putExtra("password", this.binding.editTextSenha.text.toString())
            startActivityForResult(intent, 0)
        }

        this.binding.buttonEntrar.setOnClickListener{
            val email = this.binding.editTextEmail.text.toString()
            val password = this.binding.editTextSenha.text.toString()
            this.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this, "Logado com sucesso!", Toast.LENGTH_SHORT).show()
                        Thread{
                            inserirOuAtualizarUsuarioNoBancoLocal(email, password)
                        }.start()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_LONG).show()
                    }
                }

        }
    }

    fun colocarUsernameESenha(){
        val databaseInstance = Room.databaseBuilder(this, AppDatabase::class.java, "AppDb")
            .build()
        val daoUsuario = databaseInstance.usuarioDao()

        val listaUsuarios = daoUsuario.pegarTodos()


        if(listaUsuarios.isNotEmpty()){
            val usuario = listaUsuarios[0]
            binding.editTextEmail.setText(usuario.email)
            binding.editTextSenha.setText(usuario.senha)
        }
    }

    fun inserirOuAtualizarUsuarioNoBancoLocal(email: String, password: String){
        val databaseInstance = Room.databaseBuilder(this, AppDatabase::class.java, "AppDb")
            .build()
        val daoUsuario = databaseInstance.usuarioDao()

        val usuario = daoUsuario.pegarUsuarioPorEmail(email)

        if(usuario != null && usuario.senha != password){
            usuario.senha = password
            daoUsuario.atualizarUsuario(usuario)
        } else if(usuario == null) {
            val usuario = Usuario(null, email,password)
            daoUsuario.inserir(usuario)
        }
    }


    override fun onResume() {
        super.onResume()
        Thread{
            val currentUser = auth.currentUser
            if(currentUser != null){
                print("opa")
            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        print("eta lele")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val email: String? = data?.getStringExtra("email")
            val senha: String ?= data?.getStringExtra("senha")
            binding.editTextEmail.setText(email)
            binding.editTextSenha.setText(senha)
        }
    }
}