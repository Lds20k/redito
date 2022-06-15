package com.mobile.redito

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobile.redito.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.auth = Firebase.auth
        //Verificar se usuario já esta logado
        /*
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
        */

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
                    } else {
                        Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_LONG).show()
                    }
                }

        }
    }
}