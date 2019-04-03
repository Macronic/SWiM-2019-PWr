package com.example.swim_1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val nameParam = "name"

class RunTimerActivityFragment : Fragment() {


    private var name : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            name = it.getString(nameParam) ?: ""
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_timer, container, false)

        layout.findViewById<TextView>(R.id.timerTitle).text = name
        layout.findViewById<TextView>(R.id.timerTime).text = "03:20:14"
        Log.d("timers", "new fragment name " + name)
        return layout
    }


    companion object {
        @JvmStatic
        fun newInstance(name: String) =
            RunTimerActivityFragment().apply {
                arguments = Bundle().apply {
                    putString(nameParam, name)
                }
                Log.d("timers", "new fragment >< " + name)
            }
    }
}
