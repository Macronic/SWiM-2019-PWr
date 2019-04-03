package com.example.swim_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_timers.*

class TimersActivity : AppCompatActivity() {

    lateinit var recyclerAdapter : TimerRecyclerAdapter
    lateinit var recyclerManager : RecyclerView.LayoutManager

    val timers : MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timers)

        recyclerAdapter = TimerRecyclerAdapter(timers)
        recyclerManager = LinearLayoutManager(this)

        timerList.adapter = recyclerAdapter
        timerList.layoutManager = recyclerManager
    }

    fun onAddNewTimer(view : android.view.View) {
        if (timers.count() < 4) {
            timers.add(editText.text.toString())
            recyclerAdapter.notifyDataSetChanged()
        }
    }

    fun onBackButtonClick(view : android.view.View) {
        finish()
    }

}
