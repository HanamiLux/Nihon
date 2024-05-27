package com.example.nihonhistory

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nihonhistory.databinding.ActivityPasswordRecoveryBinding

class PasswordRecoveryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordRecoveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordRecoveryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            goBackBtn.setOnClickListener {
                startActivity(Intent(this@PasswordRecoveryActivity, SignInActivity::class.java))
                finish()
            }
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@PasswordRecoveryActivity, SignInActivity::class.java))
        finish()
    }
}