package com.example.nihonhistory

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nihonhistory.databinding.ActivityCelebratesBinding
import com.example.nihonhistory.helpers.NavViewListener

class CelebratesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCelebratesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCelebratesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            navDrawerBtn.setOnClickListener {
                binding.drawerLayout.open()
            }

            NavViewListener().setup(includedNavView.navView, this@CelebratesActivity)
        }

    }
}