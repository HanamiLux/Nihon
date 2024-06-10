package com.example.nihonhistory

import android.content.Intent
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.forEye)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // Скрыть навигацию и сделать её снова видимой при свайпе
                )

        // Установка слушателя на нажатие экрана для переключения видимости навигационной панели
        window.decorView.setOnSystemUiVisibilityChangeListener {
            if (it and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION == 0) {
                window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        )
            }
        }

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

            getString(R.string.heian) -> {
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