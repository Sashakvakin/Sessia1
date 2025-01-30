package com.example.sas1601

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Session2Activity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInTextView: TextView
    private lateinit var termsCheckBox: CheckBox
    private lateinit var termsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_session2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize UI elements
        fullNameEditText = findViewById(R.id.fullNameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        signUpButton = findViewById(R.id.signUpButton)
        signInTextView = findViewById(R.id.signInTextView)
        termsCheckBox = findViewById(R.id.termsCheckBox)
        termsTextView = findViewById(R.id.termsTextView)


        // Setup click listener for sign-up button
        signUpButton.setOnClickListener {
            // Тут можно добавить логику обработки регистрации
            // Например, проверку полей, отправку данных на сервер
            // и т.д.
            Toast.makeText(this, "Sign up clicked", Toast.LENGTH_SHORT).show()
        }
        // Setup clickable text for sign-in text
        setClickableSignInText()

        // Setup change listener for checkbox
        termsCheckBox.setOnCheckedChangeListener { _, isChecked ->
            updateCheckBox(isChecked)
            updateSignUpButtonState()
        }

        setupTextWatchers()
        updateSignUpButtonState()

        setTermsTextView()

    }
    private fun setTermsTextView() {
        val fullText = getString(R.string.terms_text)
        val grayPart = "By ticking this box, you agree to our "
        val coloredPart = "Terms and conditions and private policy"

        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf(coloredPart)
        if (startIndex >= 0) {
            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#A2A2A2")), 0, grayPart.length, 0)
            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#ebbc2e")), startIndex, fullText.length, 0)
        }
        termsTextView.text = spannableString
    }

    private fun updateCheckBox(isChecked: Boolean) {
        if (isChecked) {
            // Set background color to blue
            termsCheckBox.buttonDrawable?.setColorFilter(
                ContextCompat.getColor(this, R.color.blue_sign_in),
                PorterDuff.Mode.SRC_ATOP
            )
            // Change checkmark color to white
            termsCheckBox.compoundDrawablesRelative[2]?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)

        } else {
            termsCheckBox.buttonDrawable?.clearColorFilter()
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
                    Toast.makeText(this@Session2Activity, "Sign in clicked", Toast.LENGTH_SHORT).show()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = ContextCompat.getColor(this@Session2Activity, R.color.blue_sign_in) // Установите цвет кликабельного текста
                    ds.isUnderlineText = true
                }
            }
            spannableString.setSpan(clickableSpan, startIndex, startIndex + signInText.length, 0)

            signInTextView.text = spannableString
            signInTextView.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateSignUpButtonState()
            }
        }

        fullNameEditText.addTextChangedListener(textWatcher)
        phoneEditText.addTextChangedListener(textWatcher)
        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)
        confirmPasswordEditText.addTextChangedListener(textWatcher)

    }

    private fun updateSignUpButtonState() {
        val isFullNameFilled = fullNameEditText.text.isNotBlank()
        val isPhoneFilled = phoneEditText.text.isNotBlank()
        val isEmailFilled = emailEditText.text.isNotBlank()
        val isPasswordFilled = passwordEditText.text.isNotBlank()
        val isConfirmPasswordFilled = confirmPasswordEditText.text.isNotBlank()
        val isTermsChecked = termsCheckBox.isChecked

        val isButtonEnabled = isFullNameFilled && isPhoneFilled && isEmailFilled && isPasswordFilled && isConfirmPasswordFilled && isTermsChecked
        signUpButton.isEnabled = isButtonEnabled
        signUpButton.backgroundTintList = if (isButtonEnabled) {
            ContextCompat.getColorStateList(this, R.color.blue_sign_in)
        } else {
            ContextCompat.getColorStateList(this, R.color.gray_sign_in)
        }
    }
}