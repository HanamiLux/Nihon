package com.example.nihonhistory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nihonhistory.databinding.ActivityHistoryBinding
import com.example.nihonhistory.databinding.ActivityProfileBinding
import com.example.nihonhistory.helpers.AppDatabase
import com.example.nihonhistory.helpers.HistoryRecyclerViewAdapter
import com.example.nihonhistory.helpers.NavViewListener
import com.example.nihonhistory.models.Epoch
import com.example.nihonhistory.models.TestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var testResults: TestResult? = null
    private lateinit var recyclerViewAdapter: HistoryRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = AppDatabase.getDbInstance(this)
        val userEmail = getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "")
        CoroutineScope(Dispatchers.Default).launch {
            val user = db.usersDao().getUserByEmail(userEmail!!)
            if(user != null)
                testResults = db.testResultsDao().getTestResults(user.id!!)
            launch(Dispatchers.Main) {
                binding.apply {
                    "${testResults?.iwajuku_test ?: 0}%".also { iwajukuPercentTW.text = it }
                    "${testResults?.jomon_test ?: 0}%".also { jomonPercentTW.text = it }
                    "${testResults?.yayoi_test ?: 0}%".also { yayoiPercentTW.text = it }
                    "${testResults?.yamato_test ?: 0}%".also { yamatoPercentTW.text = it }
                    "${testResults?.nara_test ?: 0}%".also { naraPercentTW.text = it }
                    "${testResults?.heian_test ?: 0}%".also { heianPercentTW.text = it }
                    "${testResults?.kamakura_test ?: 0}%".also { kamakuraPercentTW.text = it }
                    "${testResults?.muromachi_test ?: 0}%".also { muromachiPercentTW.text = it }
                    "${testResults?.azuchi_momoyama_test ?: 0}%".also { azuchiMomoyamaPercentTW.text = it }
                    "${testResults?.general_test ?: 0}%".also { generalPercentTW.text = it }
                    recyclerViewRepeat.layoutManager = LinearLayoutManager(this@ProfileActivity)
                    val sortedEpochs = listOf(
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
                    ).filter { it.testPercentage.toInt() < 80 }
                    recyclerViewAdapter = HistoryRecyclerViewAdapter(sortedEpochs)
                    recyclerViewRepeat.adapter = recyclerViewAdapter
                    if(sortedEpochs.isEmpty()){
                        repeatTW.visibility = View.GONE
                        additionalSepLine.visibility = View.GONE
                        recyclerViewRepeat.visibility = View.GONE
                    }
                    if(sortedEpochs.isEmpty()){
                        selectedCelebratesTW.visibility = View.GONE
                        additionalSepLine.visibility = View.GONE
                        recyclerViewSelectedCelebrates.visibility = View.GONE
                    }
                }
            }
        }
        binding.apply {
            navDrawerBtn.setOnClickListener {
                binding.drawerLayout.open()
            }
            iwajukuLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.iwajuku))
                startActivity(intent)
            }
            jomonLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.jomon))
                startActivity(intent)
            }
            yayoiLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.yayoi))
                startActivity(intent)
            }
            yamatoLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.yamato))
                startActivity(intent)
            }
            naraLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.nara))
                startActivity(intent)
            }
            heianLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.heian))
                startActivity(intent)
            }
            kamakuraLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.kamakura))
                startActivity(intent)
            }
            muromachiLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.muromachi))
                startActivity(intent)
            }
            azuchiMomoyamaLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.azuchi_momoyama))
                startActivity(intent)
            }
            generalLayout.setOnClickListener {
                val intent = Intent(this@ProfileActivity, TestEpochActivity::class.java)
                intent.putExtra("testEpochTitle", getString(R.string.general))
                startActivity(intent)
            }


            emailTW.text = userEmail
            NavViewListener().setup(includedNavView.navView, this@ProfileActivity)
        }
    }
}