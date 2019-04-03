package com.example.swim_1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ratedog.*


class RateDogActivity : AppCompatActivity() {

    private lateinit var dogRatingAdapter: DogRatingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratedog)
        setSupportActionBar(rateDogToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        dogRatingAdapter = DogRatingAdapter(supportFragmentManager)
        dogPager.adapter = dogRatingAdapter
        dogRatingAdapter.addDoggo(DogInfo("Fine doggo", "https://www.akc.org/wp-content/themes/akc/component-library/assets/img/welcome.jpg", true))
        dogRatingAdapter.addDoggo(DogInfo("Other doggo", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Bow_bow.jpg/150px-Bow_bow.jpg", true))
        dogRatingAdapter.addDoggo(DogInfo("Weird doggo", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Cat03.jpg/1024px-Cat03.jpg", false))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.rate_dog_toolbar_menu, menu)
        return true
    }

    fun selectedDoggo() : DogInfo {
        return dogRatingAdapter.getDoggo(dogPager.currentItem)
    }

    fun onBackButtonClick(view : android.view.View) {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.fetchTest -> {
            val selected = selectedDoggo()
            if (selected.canFetch) {
                Toast.makeText(this, R.string.dogDoesFetch, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.dogCannotFetch, Toast.LENGTH_SHORT).show();
            }
            true
        }
        R.id.petDog -> {
            Toast.makeText(this, R.string.woof, Toast.LENGTH_SHORT).show()
            true
        }
        R.id.reportDogMenuItem -> {
            val dialog = ReportDogDialogFragment()
            dialog.show(supportFragmentManager, "ReportDogDialogFragment")
            true
        }
        android.R.id.home -> {
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }

    }
}
