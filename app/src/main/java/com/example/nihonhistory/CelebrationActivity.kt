package com.example.nihonhistory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.nihonhistory.databinding.ActivityCelebrationBinding
import com.example.nihonhistory.helpers.AppDatabase
import com.example.nihonhistory.helpers.NavViewListener
import com.example.nihonhistory.models.Celebrate
import com.example.nihonhistory.models.SelectedCelebrate
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CelebrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCelebrationBinding
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCelebrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email = getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "")
        db = AppDatabase.getDbInstance(this)
        val currentCelebrate =
            Gson().fromJson(intent.getStringExtra("Holiday"), Celebrate::class.java)
        binding.apply {
            titleTW.text = currentCelebrate.name
            if (currentCelebrate.startDate == currentCelebrate.endDate)
                dateTW.text = "${currentCelebrate.startDate}"
            else
                ("${currentCelebrate.startDate} - ${currentCelebrate.endDate}").also {
                    dateTW.text = it
                }
            descriptionTW.text = currentCelebrate.description

            goBackBtn.setOnClickListener {
                startActivity(Intent(this@CelebrationActivity, CelebratesActivity::class.java))
                finish()
            }
            //Selection initializing
            lifecycleScope.launch(Dispatchers.IO) {
                val user = db.usersDao().getUserByEmail(email!!)
                if (user != null) {
                    val selectedCelebrates =
                        db.selectedCelebratesDao().getSelectedCelebrates(user.id!!)

                    if (!selectedCelebrates.contains(currentCelebrate))
                        launch(Dispatchers.Main) {
                            followBtn.setImageResource(R.drawable.ic_heart_unselected)
                        }
                    else
                        launch(Dispatchers.Main) {
                            followBtn.setImageResource(R.drawable.ic_heart_selected)
                        }
                }
            }

                    //Selection change listener
            followBtn.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val user = db.usersDao().getUserByEmail(email!!)
                    if (user != null) {
                        val selectedCelebrates =
                            db.selectedCelebratesDao().getSelectedCelebrates(user.id!!)

                        if (!selectedCelebrates.contains(currentCelebrate)) {
                            db.selectedCelebratesDao().addSelectedCelebrate(
                                SelectedCelebrate(null, user.id!!, currentCelebrate.id!!)
                            )
                            launch(Dispatchers.Main) {
                                followBtn.setImageResource(R.drawable.ic_heart_selected)
                            }
                        } else {
                            db.selectedCelebratesDao().deleteSelectedCelebrate(
                                SelectedCelebrate(
                                    db.selectedCelebratesDao().getSelectedCelebrateId(currentCelebrate.id!!),
                                    user.id!!,
                                    currentCelebrate.id!!
                                )
                            )
                            launch(Dispatchers.Main) {
                                followBtn.setImageResource(R.drawable.ic_heart_unselected)
                            }

                        }
                    }

                }

                navDrawerBtn.setOnClickListener {
                    drawerLayout.open()
                }
                NavViewListener().setup(includedNavView.navView, this@CelebrationActivity)
            }
        }
    }
}