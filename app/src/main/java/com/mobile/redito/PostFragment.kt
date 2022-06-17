package com.mobile.redito

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.room.Room
import br.senac.noteapp.models.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobile.redito.databinding.FragmentPostBinding
import com.mobile.redito.models.Comunidade
import com.mobile.redito.models.Post
import com.mobile.redito.models.UsuarioFirebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PostFragment : Fragment() {

    lateinit var binding: FragmentPostBinding
    var database: FirebaseDatabase? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        database = FirebaseDatabase.getInstance()
        binding = FragmentPostBinding.inflate(inflater)
        binding.buttonEnviar.setOnClickListener(){
            val mensagem = binding.inputMensagem.text.toString()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yy hh:mm")
            val data = formatter.format(LocalDateTime.now())

            val usuarioLogado = FirebaseAuth.getInstance().currentUser?.uid

            val novoPost = Post(null, mensagem, data, 0, null)
//            val userIdLogado = FirebaseAuth.getInstance().currentUser?.uid
            var idComunidade: String? = null

            Thread{
                pegarETratar(novoPost)
            }.start()


//             var ref = userIdLogado?.let { it1 -> database?.reference?.child("usuarios")?.child(it1) }

            //Cria o listener de mudanças do nó no Firebase
//            val itemListener = object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    //Função executada quando algo no nó mudar
//                    //Trata os dados do produto no nó
//                    dataSnapshot.children.forEach {
//                        val prod = it.getValue(String::class.java)
//                        prod?.let {
//                            idComunidade = it
//                        }
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {}
//            }
//
//            //Configura o listener no nó de produtos
//            ref?.child("produtos")?.addValueEventListener(itemListener)

//            userIdLogado?.let { it ->
//                var ref = database?.reference?.child("usuarios")?.child(it)?.child("idComunidade")?.get()?.result
//                val sal = null
//                ref?.addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(dataSnapshot: DataSnapshot) {
//                        idComunidade = dataSnapshot.getValue(String::class.java)
//                    }
//
//                    override fun onCancelled(databaseError: DatabaseError) {
//                        Log.e("","")
//                    }
//                })
//            }
        }
        return binding.root
    }
    fun pegarETratar(post: Post){
        val databaseInstance =
            activity?.let {
                Room.databaseBuilder( it.applicationContext , AppDatabase::class.java, "AppDb")
                    .build()
            }
        val daoUsuario = databaseInstance?.usuarioDao()
        val usuario = daoUsuario?.pegarTodos()?.get(0)

        val comunidade = usuario?.idComunidade

        val postNo = database?.reference?.child("comunidades/$comunidade/posts")?.push()
        post.id = postNo?.key
        postNo?.setValue(post)
    }
}