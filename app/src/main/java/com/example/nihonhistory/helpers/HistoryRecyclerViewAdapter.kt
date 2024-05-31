package com.example.nihonhistory.helpers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.example.nihonhistory.LearnEpochActivity
import com.example.nihonhistory.R
import com.example.nihonhistory.TestEpochActivity
import com.example.nihonhistory.models.Epoch

class HistoryRecyclerViewAdapter(private val items: List<Epoch>) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {
    private lateinit var context: Context
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val epochNameTW: TextView = itemView.findViewById(R.id.epochNameTW)
        val epochPeriodTW: TextView = itemView.findViewById(R.id.epochPeriodTW)
        val epochPercentageTW: TextView = itemView.findViewById(R.id.testPercentageTW)
        val epochIW: RelativeLayout = itemView.findViewById(R.id.imageBg)
        val epochTestBtn: Button = itemView.findViewById(R.id.testBtn)
        val goBtn: Button = itemView.findViewById(R.id.goBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_epoch, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.apply {
            epochNameTW.text = currentItem.title
            epochPeriodTW.text = currentItem.period
            "${currentItem.testPercentage}%".also { epochPercentageTW.text = it }
            epochIW.setBackgroundResource(currentItem.imageResId)
            if (currentItem.title != context.getString(R.string.general)) {
                goBtn.setOnClickListener {
                    val intent = Intent(context, LearnEpochActivity::class.java)
                    intent.putExtra("epochTitle", currentItem.title)
                    context.startActivity(intent)

                }
            }
            else{ // If it's a general test card
                goBtn.visibility = View.GONE
                (epochTestBtn.layoutParams as ViewGroup.MarginLayoutParams).height +=20
                epochPercentageTW.textSize = 36F
            }
                epochTestBtn.setOnClickListener {
                    val intent = Intent(context, TestEpochActivity::class.java)
                    intent.putExtra("testEpochTitle", currentItem.title)
                    context.startActivity(intent)
                }


        }


    }
}