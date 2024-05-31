package com.example.nihonhistory

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nihonhistory.databinding.ActivityLearnEpochBinding
import com.example.nihonhistory.databinding.ActivityTestEpochBinding
import com.example.nihonhistory.helpers.NavViewListener

class TestEpochActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestEpochBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestEpochBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val epochTitle = intent.getStringExtra("testEpochTitle")

        binding.apply{
            goBackBtn.setOnClickListener{ onBackPressed()}
            epochTitleTW.text = epochTitle

            when(epochTitle){
                getString(R.string.iwajuku) -> {

                }
                getString(R.string.jomon) -> {

                }
                getString(R.string.yayoi) -> {

                }
                getString(R.string.yamato) -> {

                }
                getString(R.string.nara) -> {

                }
                getString(R.string.heyan) -> {

                }
                getString(R.string.kamakura) -> {

                }
                getString(R.string.muromachi) -> {

                }
                getString(R.string.azuchi_momoyama) -> {
                    epochTitleTW.textSize = 32F
                    (epochTitleTW.layoutParams as ViewGroup.MarginLayoutParams).marginStart = 100
                }
            }
        }




    }

    override fun onBackPressed() {
            super.onBackPressed()
    }
}