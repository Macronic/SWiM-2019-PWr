package com.example.swim_1

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_run_timer.*

class RunTimerActivity : FragmentActivity() {

    var timers : MutableList<String> = ArrayList()
    var first = true

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
        var newTimerIndex = supportFragmentManager.backStackEntryCount

        if (!first) {
            newTimerIndex += 1
        }

        if (newTimerIndex >= timers.size) {
            Toast.makeText(this, R.string.noMoreTimers, Toast.LENGTH_SHORT).show()
            return
        }

        val transaction = supportFragmentManager.beginTransaction()

        if (!first) {
            transaction.addToBackStack("newTimerBackStack")
        }

        first = false


        transaction.replace(R.id.fragment2, RunTimerActivityFragment.newInstance(timers[newTimerIndex]))

        transaction.commit()
    }

}
