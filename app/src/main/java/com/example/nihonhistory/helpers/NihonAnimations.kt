package com.example.nihonhistory.helpers

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object NihonAnimations {
    fun fadingViewAnimate(textView: View, isVisible: Boolean){
        MainScope().launch {
            withContext(Dispatchers.Main) {
                if (isVisible) {
                    textView.alpha = 0f
                    textView.visibility = View.VISIBLE
                    textView.animate().alpha(1f).setDuration(500).start()
                } else {
                    textView.animate().alpha(0f).setDuration(500).withEndAction {
                        textView.visibility = View.GONE
                    }.start()
                }
            }
        }
    }
}