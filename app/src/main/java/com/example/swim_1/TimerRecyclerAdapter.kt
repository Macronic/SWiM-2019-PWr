package com.example.swim_1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimerRecyclerAdapter(private val timers : List<String>) : RecyclerView.Adapter<TimerRecyclerAdapter.TimerViewHolder>() {

    class TimerViewHolder(val layout: RelativeLayout) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TimerViewHolder {

        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.timer_element_layout, parent, false) as RelativeLayout

        return TimerViewHolder(layout)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.layout.findViewById<TextView>(R.id.timerTextView).text = timers[position]
    }

    override fun getItemCount() = timers.count()
}