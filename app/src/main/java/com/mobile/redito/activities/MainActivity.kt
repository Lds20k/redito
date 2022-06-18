package com.mobile.redito.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobile.redito.PostFragment
import com.mobile.redito.R
import com.mobile.redito.databinding.ActivityMainBinding
import com.mobile.redito.databinding.FragmentPostBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    val frag = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
                }
                R.id.discover -> {
//                    val frag = AlbumsFragment()
//                    supportFragmentManager.beginTransaction().replace(R.id.ContainerView, frag).commit()
                }
                R.id.profile -> {
                    val frag = ProfileFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
                }
                R.id.post -> {
                    val frag = PostFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
                }
                else -> {
                    val frag = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
                }

            }
            true
        }
    }
}