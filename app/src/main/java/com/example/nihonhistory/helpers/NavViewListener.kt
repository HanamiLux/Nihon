package com.example.nihonhistory.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.nihonhistory.AboutAppActivity
import com.example.nihonhistory.CelebratesActivity
import com.example.nihonhistory.HistoryActivity
import com.example.nihonhistory.ProfileActivity
import com.example.nihonhistory.R
import com.example.nihonhistory.SettingsActivity
import com.example.nihonhistory.ShareAppActivity
import com.example.nihonhistory.SignInActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NavViewListener {
    fun setup(navView: NavigationView, activity: Activity) {
        val userEmail = activity.getSharedPreferences("User", Context.MODE_PRIVATE).getString("email", "")
        navView.getHeaderView(0).findViewById<TextView>(R.id.emailTW).text = userEmail
        navView.setNavigationItemSelectedListener { item ->
            val handled = when (item.itemId) {
                R.id.profile -> {
                    activity.startActivity(Intent(activity, ProfileActivity::class.java))
                    true
                }
                R.id.history -> {
                    activity.startActivity(Intent(activity, HistoryActivity::class.java))
                    true
                }

                R.id.celebrates -> {
                    activity.startActivity(Intent(activity, CelebratesActivity::class.java))
                    true
                }

                R.id.settings -> {
                    activity.startActivity(Intent(activity, SettingsActivity::class.java))
                    true
                }

                R.id.aboutApp -> {
                    activity.startActivity(Intent(activity, AboutAppActivity::class.java))
                    true
                }

                R.id.shareApp -> {
                    activity.startActivity(Intent(activity, ShareAppActivity::class.java))
                    true
                }

                R.id.signOut -> {
                    val spEditor = activity.getSharedPreferences("User", Context.MODE_PRIVATE).edit()
                    spEditor.putString("email", "").apply()
                    Firebase.auth.signOut()
                    activity.startActivity(Intent(activity, SignInActivity::class.java))
                    true
                }

                else -> false
            }
            if(handled)
                activity.finish()
            handled
        }
    }
}