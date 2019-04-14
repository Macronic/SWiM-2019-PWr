package com.example.swim_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_dog.*

class AddDogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dog)
        dogPhoto.setOnClickListener { this.startActivity(Intent(this, TakePhotoActivity::class.java)) }
    }

}
