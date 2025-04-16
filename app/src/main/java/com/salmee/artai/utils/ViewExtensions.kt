package com.salmee.artai.utils

import android.view.View

fun View.animateClickEffect() {
    this.animate()
        .scaleX(1.1f)
        .scaleY(1.1f)
        .setDuration(100)
        .withEndAction {
            this.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(100)
        }
}
