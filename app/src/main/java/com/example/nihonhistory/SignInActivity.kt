package com.example.nihonhistory

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nihonhistory.databinding.ActivitySignInBinding
import com.example.nihonhistory.helpers.AppDatabase
import com.example.nihonhistory.helpers.HashPassword
import com.example.nihonhistory.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        if(auth.currentUser != null){
            startActivity(Intent(this@SignInActivity, HistoryActivity::class.java))
            finish()
        }

        binding.apply {
            goToSignUpBtn.setOnClickListener {
                startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
                finish()
            }
            goToRecoveryBtn.setOnClickListener {
                startActivity(Intent(this@SignInActivity, PasswordRecoveryActivity::class.java))
                finish()
            }
            signInBtn.setOnClickListener {
                signIn()
            }
        }

    }

    /** Sign in logic*/
    private fun signIn() = with(binding){
        val db = AppDatabase.getDbInstance(this@SignInActivity).usersDao()
        val email = emailET.text.toString().trim()
        val password = passwordET.text.toString().trim()
        val hashedPassword = HashPassword.hashPassword(password)

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@SignInActivity, getString(R.string.emptyFields), Toast.LENGTH_SHORT).show()
            return@with
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(null, hashedPassword, email)
                    CoroutineScope(Dispatchers.Default).launch {
                        if (db.getUserByEmail(email) == null) {
                            db.insertUser(user)
                        }
                    }
                    val spEditor = getSharedPreferences("User", Context.MODE_PRIVATE).edit()
                    spEditor.putString("email", user.email).apply()
                    startActivity(Intent(this@SignInActivity, HistoryActivity::class.java))
                    finish()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this@SignInActivity,
                    getString(R.string.incorrectLogin), Toast.LENGTH_SHORT).show()
            }
    }
}