package com.example.nihonhistory.helpers

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object NihonAnimations {
    fun fadingViewAnimate(view: View, isVisible: Boolean, invisibilityType: Int = View.GONE, duration: Long = 300) {
        MainScope().launch {
            withContext(Dispatchers.Main) {
                if (isVisible) {
                    view.alpha = 0f
                    view.translationY = -view.height.toFloat()
                    view.isEnabled = false
                    view.visibility = View.VISIBLE

                    //opacity + translation downward animation
                    view.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(duration)
                        .setListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(animation: Animator) {
                                // No action needed
                            }

                            override fun onAnimationEnd(animation: Animator) {
                                view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                                view.requestLayout()
                                view.isEnabled = true
                            }

                            override fun onAnimationCancel(animation: Animator) {
                                // No action needed
                            }

                            override fun onAnimationRepeat(animation: Animator) {
                                // No action needed
                            }
                        })
                        .start()
                } else {
                    //opacity + translation upward animation
                    view.isEnabled = false
                    view.animate()
                        .alpha(0f)
                        .translationY(-view.height.toFloat())
                        .setDuration(duration)
                        .withEndAction {
                            view.visibility = invisibilityType
                            view.translationY = 0f
                            view.layoutParams.height = view.height
                            view.requestLayout()
                        }
                        .start()
                }
            }
        }
    }

}