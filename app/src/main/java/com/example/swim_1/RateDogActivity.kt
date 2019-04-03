package com.example.swim_1

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ratedog.*
import kotlinx.android.synthetic.main.dog_toast_view.*


class RateDogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratedog)

        dogView.setOnLongClickListener { v -> onDogLongClick(v) }
    }


    fun onBackButtonClick(view : android.view.View) {
        finish()
    }

    fun onSendButtonClick(view : android.view.View) {
        Toast.makeText(this, getString(R.string.messageSent), Toast.LENGTH_LONG).show()
    }

    fun onDisplayDogChanged(view : android.view.View) {
        if (showDogSwitch.isChecked)
            dogView.visibility = ImageView.VISIBLE
        else
            dogView.visibility = ImageView.INVISIBLE
    }

    fun onDogLongClick(view : android.view.View): Boolean {
        val toast = Toast(this)
        toast.view = layoutInflater.inflate(R.layout.dog_toast_view, dogToastView)
        toast.show()
        return true
    }
}
