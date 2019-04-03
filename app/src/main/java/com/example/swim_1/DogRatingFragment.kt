package com.example.swim_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dog_toast_view.*

class DogRatingFragment : Fragment() {
    var review = ""
    var visibleDoggo = false
    var rating = 5f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val dogRatingPage = inflater.inflate(R.layout.dog_rating_page, container, false) as ViewGroup

        arguments?.takeIf { it.containsKey("pathToPhoto") }?.apply {
            val dogPhoto = dogRatingPage.findViewById<ImageView>(R.id.dogView)
            Picasso.get().load(getString("pathToPhoto")).into(dogPhoto)
            dogPhoto.setOnLongClickListener {
                val toast = Toast(activity)
                toast.view = layoutInflater.inflate(R.layout.dog_toast_view, dogToastView)
                toast.show()
                true
            }

            val dogSwitch = dogRatingPage.findViewById<Switch>(R.id.showDogSwitch)
            dogSwitch.setOnCheckedChangeListener { _, state ->
                if (state)
                    dogPhoto.visibility = ImageView.VISIBLE
                else
                    dogPhoto.visibility = ImageView.INVISIBLE
            }

            dogSwitch.isChecked = visibleDoggo

            dogRatingPage.findViewById<RatingBar>(R.id.dogRatingBar).rating = rating
            dogRatingPage.findViewById<EditText>(R.id.reviewEditText).setText(review)
            dogRatingPage.findViewById<Button>(R.id.sendButton).setOnClickListener {
                Toast.makeText(activity, getString(R.string.messageSent), Toast.LENGTH_LONG).show()
            }
        }

        return dogRatingPage
    }

    override fun onDestroyView() {
        review = view?.findViewById<EditText>(R.id.reviewEditText)?.text?.toString() ?: ""
        visibleDoggo = view?.findViewById<Switch>(R.id.showDogSwitch)?.isChecked ?: false
        rating = view?.findViewById<RatingBar>(R.id.dogRatingBar)?.rating ?: 5f
        super.onDestroyView()
    }

}