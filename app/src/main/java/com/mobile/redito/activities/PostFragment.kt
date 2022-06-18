package com.mobile.redito.activities

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.room.Room
import br.senac.noteapp.models.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mobile.redito.databinding.FragmentPostBinding
import com.mobile.redito.models.Comunidade
import com.mobile.redito.models.Post
import com.mobile.redito.models.UsuarioFirebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private lateinit var database: FirebaseDatabase


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        database = FirebaseDatabase.getInstance()
        binding = FragmentPostBinding.inflate(inflater)
        binding.buttonEnviar.setOnClickListener{
            val mensagem = binding.inputMensagem.text.toString()
            val userIdLogado = FirebaseAuth.getInstance().currentUser?.uid
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yy hh:mm")
            val data = formatter.format(LocalDateTime.now())

            database.reference.child("usuarios/${userIdLogado}").get().addOnSuccessListener { it ->
                it?.let { it ->
                    val userObject = it.getValue(UsuarioFirebase::class.java)
                    userObject?.let { user ->
                        database.reference.child("comunidades/${user.idComunidade}").get().addOnSuccessListener { it2 ->
                            it2?.let { it2 ->
                                it2.getValue(Comunidade::class.java)?.let { comunidade ->
                                    val post = Post(null, comunidade.id, it.key.toString(), user.username, mensagem, data, 0)

                                    val novoPost = database.reference.child("posts").push()
                                    post.id = novoPost.key
                                    novoPost.setValue(post)
                                }
                            }
                        }
                    }
                }
            }

        }
        return binding.root
    }
}