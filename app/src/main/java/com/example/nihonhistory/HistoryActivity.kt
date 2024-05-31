package com.example.nihonhistory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nihonhistory.databinding.ActivityHistoryBinding
import com.example.nihonhistory.helpers.HistoryRecyclerViewAdapter
import com.example.nihonhistory.helpers.NavViewListener
import com.example.nihonhistory.models.Epoch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var recyclerViewAdapter: HistoryRecyclerViewAdapter
    private lateinit var epochs: List<Epoch>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        epochs = listOf(
            Epoch(getString(R.string.iwajuku), getString(R.string.iwajukuPeriod), "100", R.drawable.card_iwajuku),
            Epoch(getString(R.string.jomon), getString(R.string.jomonPeriod), "50", R.drawable.card_jomon),
            Epoch(getString(R.string.yayoi), getString(R.string.yayoiPeriod), "10", R.drawable.card_yayoi),
            Epoch(getString(R.string.yamato), getString(R.string.yamatoPeriod), "40", R.drawable.card_yamato),
            Epoch(getString(R.string.nara), getString(R.string.naraPeriod), "42", R.drawable.card_nara),
            Epoch(getString(R.string.heyan), getString(R.string.heyanPeriod), "44", R.drawable.card_heyan),
            Epoch(getString(R.string.kamakura), getString(R.string.kamakuraPeriod), "99", R.drawable.card_kamakura),
            Epoch(getString(R.string.muromachi), getString(R.string.muromachiPeriod), "100", R.drawable.card_muromachi),
            Epoch(getString(R.string.azuchi_momoyama), getString(R.string.azuchi_momoyamaPeriod), "98", R.drawable.card_azuchi_momoyama),
            Epoch(getString(R.string.general), getString(R.string.generalPeriod), "70", R.drawable.card_general),
        )
        binding.apply{
            navDrawerBtn.setOnClickListener {
                drawerLayout.open()
            }
            NavViewListener().setup(includedNavView.navView, this@HistoryActivity)
            recyclerView.layoutManager = LinearLayoutManager(this@HistoryActivity)
            recyclerViewAdapter = HistoryRecyclerViewAdapter(epochs)
            recyclerView.adapter = recyclerViewAdapter
        }

    }

    override fun onBackPressed() = with(binding) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.close()
        else
            super.onBackPressed()
    }
}