package com.example.nihonhistory

import CelebrateRecyclerViewAdapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nihonhistory.databinding.ActivityCelebratesBinding
import com.example.nihonhistory.helpers.AppDatabase
import com.example.nihonhistory.helpers.NavViewListener
import com.example.nihonhistory.helpers.NihonAnimations
import com.example.nihonhistory.models.Celebrate
import com.example.nihonhistory.models.CelebrateData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CelebratesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCelebratesBinding
    private lateinit var db: AppDatabase
    private lateinit var recyclerViewAdapter: CelebrateRecyclerViewAdapter
    private lateinit var celebrates: List<Celebrate>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCelebratesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email = getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "")
        db = AppDatabase.getDbInstance(this)
        lifecycleScope.launch(Dispatchers.IO) {
            celebrates = db.celebratesDao().getCelebrates(Date().time)
            if(celebrates.isEmpty()) {
                val newCelebrates = readYamlFile()
                db.celebratesDao().insertAllCelebrates(newCelebrates)
            }
            val user = db.usersDao().getUserByEmail(email!!)
            lateinit var selectedCelebrates: List<Celebrate>
            if(user != null)
                selectedCelebrates = db.selectedCelebratesDao().getSelectedCelebrates(user.id!!)
            launch(Dispatchers.Main) {
                binding.apply {
                    recyclerViewCelebrates.layoutManager = LinearLayoutManager(this@CelebratesActivity)
                    recyclerViewAdapter = CelebrateRecyclerViewAdapter(selectedCelebrates, email, lifecycleScope, celebrates)
                    recyclerViewCelebrates.adapter = recyclerViewAdapter
                }
            }
        }
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
                    NihonAnimations.fadingViewAnimate(filterLayout, false)
                else
                    NihonAnimations.fadingViewAnimate(filterLayout, true)
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
                checkForFilters()
            }

            filterAutumnBtn.setOnClickListener {
                setSelectionFilterBtn(filterAutumnBtn)
                checkForFilters()
            }

            filterWinterBtn.setOnClickListener {
                setSelectionFilterBtn(filterWinterBtn)
                checkForFilters()
            }

            filterSummerBtn.setOnClickListener {
                setSelectionFilterBtn(filterSummerBtn)
                checkForFilters()
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

    private fun readYamlFile(): List<Celebrate> {
        val inputStream: InputStream = resources.openRawResource(R.raw.holidays_detailed)
        val mapper = ObjectMapper(YAMLFactory()).registerModules(KotlinModule.Builder().build())
        val celebratesMap: Map<String, CelebrateData> = mapper.readValue(inputStream)
        val celebrates = celebratesMap.entries.map { entry ->
            val value = entry.value
            Celebrate(
                id = null,
                name = value.name_ru,
                startDate = value.date,
                endDate = value.date,
                description = value.description
            )
        }

        return celebrates
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
    }
    private fun checkForFilters() = with(binding){
        if(filterNameET.text.isNotEmpty() || filterEndDateET.text.isNotEmpty() ||
            filterStartDateET.text.isNotEmpty() || !filterAllSeasonsBtn.isSelected)
            filterBtn.setImageResource(R.drawable.ic_filter_used)
        else
            filterBtn.setImageResource(R.drawable.ic_filter)
        filtersFetch()
    }

    private fun filtersFetch() = with(binding) {
        lifecycleScope.launch (Dispatchers.Main) {
            val name = if (filterNameET.text.isNotEmpty()) filterNameET.text.toString() else null
            val endDate: Long = try {
                if (filterEndDateET.text.isNotEmpty()) {
                    val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(filterEndDateET.text.toString())?.time
                    date?.let {
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = it
                        calendar.add(Calendar.DAY_OF_YEAR, -1)
                        calendar.timeInMillis
                    } ?: 0
                } else {
                    0
                }
            } catch (e: Exception) {
                0
            }
            val startDate: Long = try {
                if (filterStartDateET.text.isNotEmpty())
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(filterStartDateET.text.toString())?.time ?: Date().time
                else
                    Date().time
            } catch (e: Exception) {
                Date().time
            }

            val newCelebrates = db.celebratesDao().getCelebratesByFilters(
                name,
                endDate,
                startDate,
                !filterSpringBtn.isSelected,
                !filterSummerBtn.isSelected,
                !filterAutumnBtn.isSelected,
                !filterWinterBtn.isSelected
            )

            recyclerViewAdapter.updateData(newCelebrates)
        }
    }


}

