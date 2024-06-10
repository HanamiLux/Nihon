package com.example.nihonhistory
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nihonhistory.databinding.ActivityHistoryBinding
import com.example.nihonhistory.helpers.AppDatabase
import com.example.nihonhistory.helpers.HistoryRecyclerViewAdapter
import com.example.nihonhistory.helpers.NavViewListener
import com.example.nihonhistory.models.Epoch
import com.example.nihonhistory.models.TestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var recyclerViewAdapter: HistoryRecyclerViewAdapter
    private lateinit var epochs: List<Epoch>
    private var testResults: TestResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = AppDatabase.getDbInstance(this)
        val userEmail = getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "")
        lifecycleScope.launch(Dispatchers.Default) {
            val user = db.usersDao().getUserByEmail(userEmail!!)
            if(user != null)
                testResults = db.testResultsDao().getTestResults(user.id!!)
            epochs = listOf(
                Epoch(getString(R.string.iwajuku), getString(R.string.iwajukuPeriod), "${testResults?.iwajuku_test ?: 0}", R.drawable.card_iwajuku),
                Epoch(getString(R.string.jomon), getString(R.string.jomonPeriod), "${testResults?.jomon_test ?: 0}", R.drawable.card_jomon),
                Epoch(getString(R.string.yayoi), getString(R.string.yayoiPeriod), "${testResults?.yayoi_test ?: 0}", R.drawable.card_yayoi),
                Epoch(getString(R.string.yamato), getString(R.string.yamatoPeriod), "${testResults?.yamato_test ?: 0}", R.drawable.card_yamato),
                Epoch(getString(R.string.nara), getString(R.string.naraPeriod), "${testResults?.nara_test ?: 0}", R.drawable.card_nara),
                Epoch(getString(R.string.heian), getString(R.string.heianPeriod), "${testResults?.heian_test ?: 0}", R.drawable.card_heian),
                Epoch(getString(R.string.kamakura), getString(R.string.kamakuraPeriod), "${testResults?.kamakura_test ?: 0}", R.drawable.card_kamakura),
                Epoch(getString(R.string.muromachi), getString(R.string.muromachiPeriod), "${testResults?.muromachi_test ?: 0}", R.drawable.card_muromachi),
                Epoch(getString(R.string.azuchi_momoyama), getString(R.string.azuchi_momoyamaPeriod), "${testResults?.azuchi_momoyama_test ?: 0}", R.drawable.card_azuchi_momoyama),
                Epoch(getString(R.string.general), getString(R.string.generalPeriod), "${testResults?.general_test ?: 0}", R.drawable.card_general),
            )
            launch(Dispatchers.Main) {
                binding.apply {
                    recyclerView.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    recyclerViewAdapter = HistoryRecyclerViewAdapter(epochs)
                    recyclerView.adapter = recyclerViewAdapter
                }
            }
        }

        binding.apply{
            navDrawerBtn.setOnClickListener {
                drawerLayout.open()
            }
            NavViewListener().setup(includedNavView.navView, this@HistoryActivity)
        }

    }

    override fun onBackPressed() = with(binding) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.close()
        else
            super.onBackPressed()
    }
}