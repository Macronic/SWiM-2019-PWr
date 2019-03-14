package layout

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.swim_1.R
import org.w3c.dom.Text

class TimerRecyclerAdapter(private val timers : List<String>) : RecyclerView.Adapter<TimerRecyclerAdapter.TimerViewHolder>() {

    class TimerViewHolder(val layout: RelativeLayout) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TimerRecyclerAdapter.TimerViewHolder {

        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.timer_element_layout, parent, false) as RelativeLayout

        return TimerViewHolder(layout)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.layout.findViewById<TextView>(R.id.timerTextView).text = timers[position]
    }

    override fun getItemCount() = timers.count()
}