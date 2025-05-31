package com.salmee.artai.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.salmee.artai.R
import com.salmee.artai.fragments.PaintFragment

/**
 * Activity for displaying the paint grid in a fragment
 */
class PaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)

        if (savedInstanceState == null) {
            // Add the fragment only if this is the first creation of the activity
            val paintFragment = PaintFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, paintFragment)
                .commit()
        }
    }
}