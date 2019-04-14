package com.example.swim_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.swim_1.DogDatabase.DogInfo

class DogRatingAdapter(fragmentManager : FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    private val doggos : ArrayList<DogInfo> = ArrayList()

    fun addDoggo(info : DogInfo) {
        doggos.add(info)
        notifyDataSetChanged()
    }

    fun getDoggo(i: Int) : DogInfo {
        return doggos[i]
    }

    override fun getCount(): Int = doggos.count()

    override fun getItem(i: Int): Fragment {
        val fragment = DogRatingFragment()
        fragment.arguments = Bundle().apply {
            putString("pathToPhoto", doggos[i].pathToPhoto)
        }
        return fragment
    }

    override fun getPageTitle(i: Int): CharSequence {
        return doggos[i].name
    }

}