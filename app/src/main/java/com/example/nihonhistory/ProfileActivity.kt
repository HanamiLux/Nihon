package com.example.nihonhistory

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nihonhistory.databinding.ActivityHistoryBinding
import com.example.nihonhistory.databinding.ActivityProfileBinding
import com.example.nihonhistory.helpers.NavViewListener

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            navDrawerBtn.setOnClickListener {
                binding.drawerLayout.open()
            }

            NavViewListener().setup(includedNavView.navView, this@ProfileActivity)
        }
    }
}