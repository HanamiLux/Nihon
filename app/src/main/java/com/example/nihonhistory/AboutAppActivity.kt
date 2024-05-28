package com.example.nihonhistory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nihonhistory.databinding.ActivityAboutAppBinding
import com.example.nihonhistory.helpers.NavViewListener

class AboutAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutAppBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            navDrawerBtn.setOnClickListener {
                binding.drawerLayout.open()
            }

            NavViewListener().setup(includedNavView.navView, this@AboutAppActivity)
        }

    }
}