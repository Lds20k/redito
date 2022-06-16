package com.mobile.redito.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

//        this.auth = Firebase.auth
        this.auth = FirebaseAuth.getInstance()
        //Verificar se usuario já esta logado
        val currentUser = auth.currentUser
        if(currentUser != null){
//            reload();
            this.auth.signOut();
            print("opa")
        }

        this.binding.buttonRegistrar.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra("email", this.binding.editTextEmail.text.toString())
            intent.putExtra("password", this.binding.editTextSenha.text.toString())
            startActivity(intent)
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
                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_LONG).show()
                    }
                }

        }
    }

    fun inserirOuAtualizarUsuarioNoBancoLocal(email: String, password: String){
        val databaseInstance = Room.databaseBuilder(this, AppDatabase::class.java, "AppDb")
//            .fallbackToDestructiveMigration()
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
}