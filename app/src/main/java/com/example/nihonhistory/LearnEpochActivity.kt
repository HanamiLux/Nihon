package com.example.nihonhistory

import android.content.Intent
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nihonhistory.databinding.ActivityHistoryBinding
import com.example.nihonhistory.databinding.ActivityLearnEpochBinding
import com.example.nihonhistory.helpers.HistoryRecyclerViewAdapter
import com.example.nihonhistory.helpers.NavViewListener
import com.example.nihonhistory.models.Epoch

class LearnEpochActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnEpochBinding
    private lateinit var nameOfEpoch: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnEpochBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        val epochTitle = intent.getStringExtra("epochTitle")
        when (epochTitle) {
            getString(R.string.iwajuku) -> {
                nameOfEpoch = "iwajuku"
            }

            getString(R.string.jomon) -> {
                nameOfEpoch = "jomon"
            }

            getString(R.string.yayoi) -> {
                nameOfEpoch = "yayoi"
            }

            getString(R.string.yamato) -> {
                nameOfEpoch = "yamato"
            }

            getString(R.string.nara) -> {
                nameOfEpoch = "nara"
            }

            getString(R.string.heyan) -> {
                nameOfEpoch = "heyan"
            }

            getString(R.string.kamakura) -> {
                nameOfEpoch = "kamakura"
            }

            getString(R.string.muromachi) -> {
                nameOfEpoch = "muromachi"
            }

            getString(R.string.azuchi_momoyama) -> {
                nameOfEpoch = "azuchi_momoyama"
            }
        }
        binding.apply {
            navDrawerBtn.setOnClickListener {
                drawerLayout.open()
            }
            goBackBtn.setOnClickListener { onBackPressed() }
            epochTitleTW.text = epochTitle

            NavViewListener().setup(includedNavView.navView, this@LearnEpochActivity)
            pdfView.fromAsset("${nameOfEpoch.lowercase()}.pdf").load()

        }
    }


    override fun onBackPressed() = with(binding) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.close()
        else
            super.onBackPressed()
    }
}