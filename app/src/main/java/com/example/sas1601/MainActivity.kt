package com.example.sas1601

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    data class OnboardingItem(
        val imageResId: Int,
        val titleResId: Int,
        val descriptionResId: Int,
        val showSignUp: Boolean = false
    )

    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var skipButton: Button
    private lateinit var nextButton: Button
    private lateinit var signUpButton: Button
    private lateinit var signInTextView: TextView

    private val onboardingQueue: Queue<OnboardingItem> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById(R.id.imageView2)
        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        skipButton = findViewById(R.id.button_skip)
        nextButton = findViewById(R.id.button_next)
        signUpButton = findViewById(R.id.button_sign_up)
        signInTextView = findViewById(R.id.signInTextView)

        setupOnboardingQueue()
        showNextOnboardingItem()

        nextButton.setOnClickListener {
            showNextOnboardingItem()
        }

        skipButton.setOnClickListener {
            while (onboardingQueue.size > 1) {
                onboardingQueue.poll()
            }
            showNextOnboardingItem()
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, Session2Activity::class.java)
            startActivity(intent)
        }
    }

    private fun setupOnboardingQueue() {
        onboardingQueue.add(
            OnboardingItem(
                R.drawable.in_no_time_pana_1,
                R.string.title_onboarding1,
                R.string.description_onboarding1
            )
        )
        onboardingQueue.add(
            OnboardingItem(
                R.drawable.rafiki,
                R.string.title_onboarding2,
                R.string.description_onboarding2
            )
        )
        onboardingQueue.add(
            OnboardingItem(
                R.drawable.rafiki2,
                R.string.title_onboarding3,
                R.string.description_onboarding3,
                true
            )
        )
    }

    private fun showNextOnboardingItem() {
        if (onboardingQueue.isNotEmpty()) {
            val item = onboardingQueue.poll()
            imageView.setImageResource(item.imageResId)
            titleTextView.setText(item.titleResId)
            descriptionTextView.setText(item.descriptionResId)

            if (item.showSignUp) {
                nextButton.visibility = View.GONE
                skipButton.visibility = View.GONE
                signUpButton.visibility = View.VISIBLE
                signInTextView.visibility = View.VISIBLE
                setClickableSignInText()

            } else {
                nextButton.visibility = View.VISIBLE
                skipButton.visibility = View.VISIBLE
                signUpButton.visibility = View.GONE
                signInTextView.visibility = View.GONE
            }

        }
    }

    private fun setClickableSignInText() {
        val fullText = getString(R.string.already_have_account)
        val signInText = "Sign in"
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf(signInText)
        if (startIndex >= 0){

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    // Действие при клике на "Sign in"
                    Toast.makeText(this@MainActivity, "Sign in clicked", Toast.LENGTH_SHORT).show()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = ContextCompat.getColor(this@MainActivity, R.color.blue_sign_in) // Установите цвет кликабельного текста
                    ds.isUnderlineText = true
                }
            }
            spannableString.setSpan(clickableSpan, startIndex, startIndex + signInText.length, 0)

            signInTextView.text = spannableString
            signInTextView.movementMethod = LinkMovementMethod.getInstance()
        }


    }
}