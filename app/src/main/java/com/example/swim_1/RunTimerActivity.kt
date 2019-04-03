package com.example.swim_1

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_run_timer.*

class RunTimerActivity : FragmentActivity() {

    var timers : MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_timer)
        for (string in intent.getStringArrayListExtra("timers")) {
            timers.add(string)
        }

        nextTimerButton.setOnClickListener { nextTimer() }

        nextTimer()
    }

    private fun nextTimer() {
        val newTimerIndex = supportFragmentManager.fragments.size

        if (newTimerIndex >= timers.size) {
            Toast.makeText(this, R.string.noMoreTimers, Toast.LENGTH_SHORT).show()
            return
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(RunTimerActivityFragment.newInstance(timers[newTimerIndex]), "timerFragment")
        if (newTimerIndex != 0) {
            transaction.addToBackStack("newTimerBackStack")
        }
        transaction.commit()
    }

}
