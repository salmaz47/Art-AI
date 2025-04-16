package com.salmee.artai.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.salmee.artai.R
import com.salmee.artai.databinding.ActivityDrawingBinding
import com.salmee.artai.databinding.ActivityLettersBinding
import com.salmee.artai.fragments.DrawingsFragment
import com.salmee.artai.fragments.LettersFragment


class DrawingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrawingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding= ActivityDrawingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            loadFragment(DrawingsFragment())
        }






    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }



}