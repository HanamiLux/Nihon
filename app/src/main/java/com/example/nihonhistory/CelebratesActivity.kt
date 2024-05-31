package com.example.nihonhistory

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nihonhistory.databinding.ActivityCelebratesBinding
import com.example.nihonhistory.helpers.NavViewListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            currentDateTW.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
            filterSpringBtn.isSelected = true
            filterSummerBtn.isSelected = true
            filterAutumnBtn.isSelected = true
            filterWinterBtn.isSelected = true
            filterAllSeasonsBtn.isSelected = true
            filterBtn.setOnClickListener {
                if(filterLayout.visibility == View.VISIBLE)
                    filterLayout.visibility = View.GONE
                else
                    filterLayout.visibility = View.VISIBLE
                checkForFilters()
            }
            filterAllSeasonsBtn.setOnClickListener {
                it.isSelected = !it.isSelected
                updateButtonAppearance(filterAllSeasonsBtn, it.isSelected)
                filterSpringBtn.isSelected = it.isSelected
                filterSummerBtn.isSelected = it.isSelected
                filterAutumnBtn.isSelected = it.isSelected
                filterWinterBtn.isSelected = it.isSelected
                updateButtonAppearance(filterSpringBtn, filterSpringBtn.isSelected)
                updateButtonAppearance(filterSummerBtn, filterSummerBtn.isSelected)
                updateButtonAppearance(filterAutumnBtn, filterAutumnBtn.isSelected)
                updateButtonAppearance(filterWinterBtn, filterWinterBtn.isSelected)
                checkForFilters()
            }

            filterSpringBtn.setOnClickListener {
                setSelectionFilterBtn(filterSpringBtn)
            }

            filterAutumnBtn.setOnClickListener {
                setSelectionFilterBtn(filterAutumnBtn)
            }

            filterWinterBtn.setOnClickListener {
                setSelectionFilterBtn(filterWinterBtn)
            }

            filterSummerBtn.setOnClickListener {
                setSelectionFilterBtn(filterSummerBtn)
            }
            filterNameET.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    checkForFilters()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Do nothing
                }
            })

            filterStartDateET.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    checkForFilters()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Do nothing
                }
            })

            filterEndDateET.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    checkForFilters()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Do nothing
                }
            })

            NavViewListener().setup(includedNavView.navView, this@CelebratesActivity)
        }

    }


    private fun updateButtonAppearance(button: Button, isSelected: Boolean) {
        if (isSelected) {
            button.setBackgroundColor(getColor(R.color.mainBtn))
        } else {
            button.setBackgroundColor(getColor(R.color.hints))
        }
    }
    private fun setSelectionFilterBtn(btn: Button) = with(binding){
        btn.isSelected = !btn.isSelected
        updateButtonAppearance(btn, btn.isSelected)
        filterAllSeasonsBtn.isSelected = filterSummerBtn.isSelected && filterWinterBtn.isSelected &&
                filterAutumnBtn.isSelected && filterSpringBtn.isSelected
        updateButtonAppearance(filterAllSeasonsBtn, filterAllSeasonsBtn.isSelected)
        checkForFilters()
    }
    private fun checkForFilters() = with(binding){
        if(filterNameET.text.isNotEmpty() || filterEndDateET.text.isNotEmpty() ||
            filterStartDateET.text.isNotEmpty() || !filterAllSeasonsBtn.isSelected)
            filterBtn.setImageResource(R.drawable.ic_filter_used)
        else
            filterBtn.setImageResource(R.drawable.ic_filter)
    }
}