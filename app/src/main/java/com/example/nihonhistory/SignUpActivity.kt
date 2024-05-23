package com.example.nihonhistory

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nihonhistory.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var login: String
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
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
  //      val db: AppDatabase = AppDatabase.getDbInstance(context.applicationContext)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply{

            goBackBtn.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                finish()
            }

            registerBtn.setOnClickListener {
                if(!isDataValid()) return@setOnClickListener

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener{
                        val firebaseUser : FirebaseUser? = auth.currentUser
                        Toast.makeText(this@SignUpActivity, "Пользователь ${firebaseUser?.email} зарегистрирован!", Toast.LENGTH_SHORT).show()
                        //                if(db.usersDao().getUser(login) == null){
//                    db.usersDao().insertUser(User(null, login, password))
//                }
                        startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@SignUpActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun isDataValid(): Boolean = with(binding) {

        if (passwordET.text.toString() != passwordET2.text.toString()) { // Comparison validation
            showError(passwordComparisonErrorTW, true)
            return@with false
        }
        showError(passwordComparisonErrorTW, false)
        if (emailET.text.toString().isEmpty() || passwordET.text.toString().isEmpty()) { // Empty fields validation
            showError(emptyFieldsErrorTW, true)
            return@with false
        }
        showError(emptyFieldsErrorTW, false)
        email = emailET.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // Email format validation
            showError(emailFormatErrorTW, true)
            return@with false
        }
        showError(emailFormatErrorTW, false)
        login = loginET.text.toString().trim()
        password = passwordET.text.toString().trim()

        if (!loginRegex.matches(login)) {
            showError(loginErrorTW, true)
            return@with false
        }
        showError(loginErrorTW, false)
        if (!passwordRegex.matches(password)) {
            showError(passwordErrorTW, true)
            return@with false
        }
        showError(passwordErrorTW, false)
        bottomIW.animate().alpha(1f).setDuration(300).withStartAction(){ bottomIW.visibility = View.VISIBLE }.start()
        return@with true
    }

    private fun showError(textView: TextView, isVisible: Boolean) = with(binding) {
        if (isVisible) {
            bottomIW.animate().alpha(0f).setDuration(300).withEndAction{ bottomIW.visibility = View.GONE }.start()
            textView.alpha = 0f
            textView.visibility = View.VISIBLE
            textView.animate().alpha(1f).setDuration(500).start()
        } else {
            textView.animate().alpha(0f).setDuration(500).withEndAction {
                textView.visibility = View.GONE
            }.start()
        }
    }
}