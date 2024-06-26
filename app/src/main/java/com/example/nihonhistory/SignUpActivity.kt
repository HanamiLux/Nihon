package com.example.nihonhistory

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nihonhistory.databinding.ActivitySignUpBinding
import com.example.nihonhistory.helpers.AppDatabase
import com.example.nihonhistory.helpers.HashPassword
import com.example.nihonhistory.helpers.NihonAnimations
import com.example.nihonhistory.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var email: String
    private lateinit var password: String

    //Логин состоит из букв латинского алфавита (верхнего и нижнего регистра),
    // цифр, символов подчеркивания (_) и дефисов (-), и имеет длину от 3 до 20 символов
    private val loginRegex = Regex("^[a-zA-Z0-9_-]{3,20}$")

    //Пароль содержит хотя бы одну букву в нижнем регистре,
    // хотя бы одну букву в верхнем регистре, хотя бы одну цифру и хотя бы один специальный символ, длина пароля составляет как минимум 8 символов
    private val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,}$")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val db: AppDatabase = AppDatabase.getDbInstance(this@SignUpActivity)
        binding.apply{

            goBackBtn.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                finish()
            }

            registerBtn.setOnClickListener {
                if(!isDataValid()) return@setOnClickListener
                val hashedPassword = HashPassword.hashPassword(password)

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener{
                        val firebaseUser : FirebaseUser? = auth.currentUser
                        Toast.makeText(this@SignUpActivity,
                            getString(R.string.successfullyRegistered, firebaseUser?.email), Toast.LENGTH_SHORT).show()
                        lifecycleScope.launch(Dispatchers.IO) {
                            if(db.usersDao().getUserByEmail(email) == null){
                                db.usersDao().insertUser(User(null, hashedPassword, email))
                        }
                }
                        startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@SignUpActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

/**Input fields data validation*/
    private fun isDataValid(): Boolean = with(binding) {

        if (passwordET.text.toString() != passwordET2.text.toString()) { // Comparison validation
            NihonAnimations.fadingViewAnimate(passwordComparisonErrorTW, true)
            return@with false
        }
        NihonAnimations.fadingViewAnimate(passwordComparisonErrorTW, false)
        if (emailET.text.toString().isEmpty() || passwordET.text.toString().isEmpty()) { // Empty fields validation
            NihonAnimations.fadingViewAnimate(emptyFieldsErrorTW, true)
            return@with false
        }
        NihonAnimations.fadingViewAnimate(emptyFieldsErrorTW, false)
        email = emailET.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // Email format validation
            NihonAnimations.fadingViewAnimate(emailFormatErrorTW, true)
            return@with false
        }
        NihonAnimations.fadingViewAnimate(emailFormatErrorTW, false)
        password = passwordET.text.toString().trim()

        if (!passwordRegex.matches(password)) {
            NihonAnimations.fadingViewAnimate(passwordErrorTW, true)
            return@with false
        }
        NihonAnimations.fadingViewAnimate(passwordErrorTW, false)
        return@with true
    }



    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
        finish()
    }
}