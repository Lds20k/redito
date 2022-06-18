package com.mobile.redito.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.room.Room
import br.senac.noteapp.models.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mobile.redito.databinding.FragmentProfileBinding
import com.mobile.redito.models.Post


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    var database: FirebaseDatabase? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater)

        database = FirebaseDatabase.getInstance()

        Thread{
            exibirUsername()
        }.start()

        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
        }

        return binding.root
    }

    fun exibirUsername(){
        val databaseInstance =
            activity?.let {
                Room.databaseBuilder( it.applicationContext , AppDatabase::class.java, "AppDb")
                    .build()
            }
        val daoUsuario = databaseInstance?.usuarioDao()
        val usuario = FirebaseAuth.getInstance().currentUser?.email?.let { daoUsuario?.pegarUsuarioPorEmail(it) }

        activity?.runOnUiThread(){
           binding.textUsername.setText("Username:\n" + usuario?.username)
           binding.textEmail.setText("Email:\n" + usuario?.email)
        }
    }
}