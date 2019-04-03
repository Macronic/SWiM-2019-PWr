package com.example.swim_1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_timers.*

class TimersActivity : AppCompatActivity() {

    lateinit var recyclerAdapter : TimerRecyclerAdapter
    lateinit var recyclerManager : RecyclerView.LayoutManager

    val timers : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timers)

        recyclerAdapter = TimerRecyclerAdapter(timers)
        recyclerManager = LinearLayoutManager(this)

        timerList.adapter = recyclerAdapter
        timerList.layoutManager = recyclerManager

        Glide.with(this).load("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/BahnhofsuhrZuerich_RZ.jpg/220px-BahnhofsuhrZuerich_RZ.jpg").into(clockImage)
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

    fun onRunTimersClick(view : android.view.View) {
        if (timers.size == 0) {
            Toast.makeText(this, R.string.noTimers, Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, RunTimerActivity::class.java)
        intent.putStringArrayListExtra("timers", timers)
        this.startActivity(intent)
    }

}
