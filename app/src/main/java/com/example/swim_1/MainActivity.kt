package com.example.swim_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onGoThereClick(view : android.view.View) {
        disableButtons()
        when {
            placeTimersRadioButton.isChecked -> this.startActivity(Intent(this, TimersActivity::class.java))
            rateSomeDogsRadioButton.isChecked -> this.startActivity(Intent(this, RateDogActivity::class.java))
            else -> finish()
        }

    }

    override fun onResume() {
        super.onResume()
        enableButtons()
    }

    fun disableButtons() {
        goThere.isEnabled = false
    }

    fun enableButtons() {
        goThere.isEnabled = true
    }
}
