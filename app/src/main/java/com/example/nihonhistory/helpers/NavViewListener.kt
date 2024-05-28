package com.example.nihonhistory.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.nihonhistory.AboutAppActivity
import com.example.nihonhistory.CelebratesActivity
import com.example.nihonhistory.ProfileActivity
import com.example.nihonhistory.R
import com.example.nihonhistory.SettingsActivity
import com.example.nihonhistory.ShareAppActivity
import com.example.nihonhistory.SignInActivity
import com.google.android.material.navigation.NavigationView

class NavViewListener {
    fun setup(navView: NavigationView, activity: Activity) {
        navView.setNavigationItemSelectedListener { item ->
            val handled = when (item.itemId) {
                R.id.profile -> {
                    activity.startActivity(Intent(activity, ProfileActivity::class.java))
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