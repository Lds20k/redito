package com.mobile.redito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

            this.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    if(it.isSuccessful){
                        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}