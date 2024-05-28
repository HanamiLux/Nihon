package com.example.nihonhistory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.nihonhistory.databinding.ActivityHistoryBinding
import com.example.nihonhistory.helpers.NavViewListener

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
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