package com.example.sas1601

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_splash_screen)

        val imageView: ImageView = findViewById(R.id.imageView)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Sessia1Activity::class.java))
            finish()
        }, 800)
    }
}