package com.example.swim_1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swim_1.DogDatabase.DogInfo
import com.example.swim_1.DogDatabase.DogRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_dog.*
import java.io.File

class AddDogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dog)
        dogPhoto.setOnClickListener { this.startActivityForResult(Intent(this, TakePhotoActivity::class.java), photoResultRequest) }
        addToDatabaseButton.setOnClickListener { addToDatabase() }
    }

    companion object {
        const val photoResultRequest = 1
        const val photoPathDataName = "photoPath"
    }

    var photoPath = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == photoResultRequest && resultCode == RESULT_OK) {
            photoPath = data!!.getStringExtra(photoPathDataName)
            Picasso.get().load(File(photoPath)).into(dogPhoto)
        }
    }

    fun addToDatabase() {
        if (nameEditText.text.toString() == "")
        {
            Toast.makeText(this, "You have to name your dog!", Toast.LENGTH_LONG).show()
            return
        }
        if (photoPath == "")
        {
            Toast.makeText(this, "You have to provide photo for your dog!", Toast.LENGTH_LONG).show()
            return
        }

        DogRepository(application).insertNewDog(DogInfo(0, nameEditText.text.toString(), "file:"+photoPath, fetchSwitch.isEnabled))
        finish()
    }

}
