package com.salmee.artai

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.salmee.artai.register.LoginActivity
import com.salmee.artai.register.SignupActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val loginBtn :Button =findViewById(R.id.login_btn)
        val signupBtn :Button =findViewById(R.id.signup_btn)
        val guestBtn :Button =findViewById(R.id.guest_btn)

        loginBtn.setOnClickListener {

            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)

        }

        signupBtn.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val i = Intent(this, SignupActivity::class.java)
            startActivity(i)

        }

        guestBtn.setOnClickListener {

            val intent = Intent(this, ModelActivity::class.java)
            intent.putExtra("IS_GUEST", true)  // Pass guest mode flag
            startActivity(intent)
            finish()
        }


    }
}