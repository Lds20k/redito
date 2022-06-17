package com.mobile.redito.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.mobile.redito.databinding.CardLayoutBinding
import com.mobile.redito.databinding.FragmentHomeBinding
import com.mobile.redito.models.Comunidade
import com.mobile.redito.models.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        //Cria o listener de mudanças do nó no Firebase
//        val itemListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                //Função executada quando algo no nó mudar
//                //Trata os dados do produto no nó
//                tratarDados(dataSnapshot, inflater)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                //Função chamada quando houver erro de conexão
//                Log.w("MainActivity", "onCancelled", databaseError.toException())
//            }
//        }
//
//
//        val ref = FirebaseDatabase.getInstance().reference.child("comunidades").addValueEventListener(itemListener)

//        FirebaseDatabase.getInstance().reference.child("comunidades").get()
//            .addOnCompleteListener(OnCompleteListener<DataSnapshot> { task ->
//                if (!task.isSuccessful) {
//                    tratarDados(task, inflater)
////                    Log.e("firebase", "Error getting data", task.exception)
//                } else {
////                    Log.d("firebase", task.result.value.toString())
//                }
//            })

//        FirebaseDatabase.getInstance().reference.child("comunidades").get().addOnSuccessListener {
//            tratarDados(it, inflater)
//        }

        return binding.root
    }

    //Tratar os dados da "foto" do Firebase, transformando-os em
    //uma lista de produtos e chamadno a função "atualizarTela"
    //para exibí-los
    @RequiresApi(Build.VERSION_CODES.O)
    private fun tratarDados(dataSnapshot: DataSnapshot, inflater: LayoutInflater) {
        val itemList = arrayListOf<Comunidade>()

//        val content = dataSnapshot.getValue(List::class.java)
        //Percorre os nós filhos da foto para transformá-los
        //em uma lista de produtos
        dataSnapshot.children.forEach {
            val prod = it.getValue(Comunidade::class.java)
            prod?.let{
                itemList.add(prod)
            }

        }

        //Chamar o método que lerá array itemList e atualizará a tela
        //Limpa a tela
        atualizarTela(itemList, inflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun atualizarTela(lista: List<Comunidade>, inflater: LayoutInflater){
        binding.container.removeAllViews()
        for (comunidade in lista) {
            comunidade.posts?.forEach {
                createCard(comunidade.nome,it.conteudo, it.likes, inflater ,it.hora)
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createCard(
        username: String? = null,
        message: String,
        likeCount: Int,
        inflater: LayoutInflater,
        date: String = LocalDateTime.now().toString()
    ): CardView {
        val cardBinding = CardLayoutBinding.inflate(inflater)

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yy hh:mm")
        cardBinding.textViewUsename.text = username
        cardBinding.textViewMessage.text = message
        cardBinding.textViewDateHour.text = date
        cardBinding.textViewLikeCount.text = likeCount.toString()

        return cardBinding.root
    }

}