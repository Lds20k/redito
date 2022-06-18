package com.mobile.redito.activities

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.mobile.redito.databinding.CardLayoutBinding
import com.mobile.redito.databinding.FragmentHomeBinding
import com.mobile.redito.models.Comunidade
import com.mobile.redito.models.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        FirebaseDatabase.getInstance().reference.child("posts").get().addOnSuccessListener {
            if(it.value is Map<*, *> && (it.value as Map<*, *>).isNotEmpty()){
                tratarDados(it, inflater)
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun tratarDados(dataSnapshot: DataSnapshot, inflater: LayoutInflater) {
        val posts = arrayListOf<Post>()

        dataSnapshot.children.forEach { it ->
            val post = it.getValue(Post::class.java)
            post?.let {
                posts.add(it);
            }
        }
        atualizarTela(posts, inflater)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun atualizarTela(posts: ArrayList<Post>, inflater: LayoutInflater){
        binding.container.removeAllViews()
        for(post in posts){
            binding.container.addView(createCard(post.nomeDono, post.conteudo, post.likes, post.hora, inflater))
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createCard(
        username: String? = null,
        message: String,
        likeCount: Int,
        date: String = LocalDateTime.now().toString(),
        inflater: LayoutInflater
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