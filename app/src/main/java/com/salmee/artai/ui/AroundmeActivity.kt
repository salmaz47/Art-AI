package com.salmee.artai.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.salmee.artai.R
import com.salmee.artai.databinding.ActivityAroundmeBinding
import com.salmee.artai.fragments.AroundmeHabitsFragment
  // Import the function

class AroundmeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAroundmeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityAroundmeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            loadFragment(AroundmeHabitsFragment())
        }

       // setupNavigation(this, binding.navigationBar.root) // ✅ Call the reusable function
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
