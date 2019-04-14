package com.example.swim_1

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.swim_1.DogDatabase.DogInfo
import com.example.swim_1.DogDatabase.DogRepository
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Thread.sleep


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        sleep(2000)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main)

    }

    class ResetAsyncTask(val application : Application) : AsyncTask<Unit, Unit, Unit>()  {
        override fun doInBackground(vararg p0: Unit?) {
            DogRepository(application).removeAllDogs()
            DogRepository(application).insertNewDog(
                DogInfo(0,
                    "Fine doggo",
                    "https://www.akc.org/wp-content/themes/akc/component-library/assets/img/welcome.jpg",
                    true)
            )
            DogRepository(application).insertNewDog(
                DogInfo(1,
                    "Other doggo",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Bow_bow.jpg/150px-Bow_bow.jpg",
                    true)
            )
            DogRepository(application).insertNewDog(
                DogInfo(2,
                    "Weird doggo",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Cat03.jpg/1024px-Cat03.jpg",
                    false
                )
            )
        }
    }

    fun onGoThereClick(view : android.view.View) {
        disableButtons()
        when {
            placeTimersRadioButton.isChecked -> this.startActivity(Intent(this, TimersActivity::class.java))
            rateSomeDogsRadioButton.isChecked -> this.startActivity(Intent(this, RateDogActivity::class.java))
            resetDogsRadioButton.isChecked -> {
                ResetAsyncTask(application).execute()
                enableButtons()
            }
            else -> finish()
        }

    }

    override fun onResume() {
        super.onResume()
        enableButtons()

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val selected = sharedPref.getInt("selectedButton", 0)

        when (selected) {
            0 -> placeTimersRadioButton.isChecked = true
            1 -> rateSomeDogsRadioButton.isChecked = true
            2 -> resetDogsRadioButton.isChecked = true
        }

    }

    override fun onPause() {
        val currentButton = when {
            placeTimersRadioButton.isChecked -> 0
            rateSomeDogsRadioButton.isChecked -> 1
            resetDogsRadioButton.isChecked -> 2
            else -> 0
        }
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("selectedButton", currentButton)
        editor.apply()
        super.onPause()
    }

    fun disableButtons() {
        goThere.isEnabled = false
    }

    fun enableButtons() {
        goThere.isEnabled = true
    }
}
