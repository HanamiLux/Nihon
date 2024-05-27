package com.example.nihonhistory

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nihonhistory.databinding.ActivitySignInBinding
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


        val currentUser = auth.currentUser
    }

    private fun signIn() = with(binding){
        val db = AppDatabase.getDbInstance(this@SignInActivity).usersDao()
        val email = emailET.text.toString().trim()
        val password = passwordET.text.toString().trim()
        val hashedPassword = MessageDigest.getInstance("SHA-256")
            .digest(password.toByteArray())
            .fold(""){ str, it -> str + "%02x".format(it) }
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@SignInActivity, "Заполните пустые поля!", Toast.LENGTH_SHORT).show()
            return@with
        }

        auth.signInWithEmailAndPassword(email, hashedPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    CoroutineScope(Dispatchers.Default).launch {
                        if (db.getUserByEmail(email) == null) {
                            db.insertUser(User(null, "", hashedPassword, email))
                        }
                    }
                        startActivity(Intent(this@SignInActivity, HistoryActivity::class.java))
                        finish()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this@SignInActivity, "Данные не подходят!", Toast.LENGTH_SHORT).show()
            }
    }
}