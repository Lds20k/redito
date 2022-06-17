package com.mobile.redito.activities

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.mobile.redito.databinding.CardLayoutBinding
import com.mobile.redito.databinding.FragmentHomeBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.container.addView(createCard("test", "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum ", 0, inflater))
        binding.container.addView(createCard("test", "test", 0, inflater))
        binding.container.addView(createCard("test", "test", 0, inflater))
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createCard(
        username: String,
        message: String,
        likeCount: Int,
        inflater: LayoutInflater,
        date: LocalDateTime = LocalDateTime.now()
    ): CardView {
        val cardBinding = CardLayoutBinding.inflate(inflater)

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yy hh:mm")
        cardBinding.textViewUsename.text = username
        cardBinding.textViewMessage.text = message
        cardBinding.textViewDateHour.text = formatter.format(date)
        cardBinding.textViewLikeCount.text = likeCount.toString()

        return cardBinding.root
    }

}