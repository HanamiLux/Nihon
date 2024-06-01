package com.example.nihonhistory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nihonhistory.databinding.ActivityPasswordRecoveryBinding
import com.example.nihonhistory.helpers.NihonAnimations
import com.google.firebase.auth.FirebaseAuth

class PasswordRecoveryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordRecoveryBinding
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordRecoveryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            recoverBtn.setOnClickListener{
                if(emailET.text.toString().isNotEmpty())
                    resetPassword(emailET.text.toString())
            }
            goBackBtn.setOnClickListener {
                startActivity(Intent(this@PasswordRecoveryActivity, SignInActivity::class.java))
                finish()
            }
        }

    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email.trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // В случае успешной отправки email
                    NihonAnimations.fadingViewAnimate(binding.TW, false)
                    binding.TW.text = getString(R.string.recoverySuccessfullySend)
                    NihonAnimations.fadingViewAnimate(binding.TW, true)
                } else {
                    // В случае ошибки
                    NihonAnimations.fadingViewAnimate(binding.TW, false)
                    binding.TW.text = getString(R.string.recoveryFailedSend, task.exception?.message)
                    NihonAnimations.fadingViewAnimate(binding.TW, true)
                }
            }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@PasswordRecoveryActivity, SignInActivity::class.java))
        finish()
    }
}