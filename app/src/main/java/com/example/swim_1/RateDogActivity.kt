package com.example.swim_1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.swim_1.DogDatabase.DogInfo
import com.example.swim_1.DogDatabase.DogRepository
import kotlinx.android.synthetic.main.activity_ratedog.*


class RateDogActivity : AppCompatActivity() {

    private lateinit var dogRatingAdapter: DogRatingAdapter

    var currentItem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratedog)
        setSupportActionBar(rateDogToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        dogRatingAdapter = DogRatingAdapter(supportFragmentManager)
        dogPager.adapter = dogRatingAdapter

        DogRepository(application).getDogs().observe(this, object : Observer<List<DogInfo>> {
            override fun onChanged(doggos : List<DogInfo>?) {
                dogRatingAdapter.removeDoggos()
                doggos ?: return

                for (dog in doggos) {
                    dogRatingAdapter.addDoggo(dog)
                }

            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.rate_dog_toolbar_menu, menu)
        return true
    }

    private fun selectedDoggo() : DogInfo? {
        return dogRatingAdapter.getDoggo(dogPager.currentItem)
    }

    fun onBackButtonClick(view : android.view.View) {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.fetchTest -> {
            val selected = selectedDoggo()
            if (selected != null) {
                if (selected.canFetch) {
                    Toast.makeText(this, R.string.dogDoesFetch, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.dogCannotFetch, Toast.LENGTH_SHORT).show();
                }
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
        R.id.addNewDogMenu -> {
            this.startActivity(Intent(this, AddDogActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }

    }
}
