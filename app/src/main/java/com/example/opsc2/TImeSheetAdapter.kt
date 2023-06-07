import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc2.R
import com.example.opsc2.TimeSheet

class TimesheetAdapter(private val context: Context, private val timesheetList: List<TimeSheet>) :
    RecyclerView.Adapter<TimesheetAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define your ViewHolder components here
        val timesheetNameTextView: TextView = itemView.findViewById(R.id.timesheetNameTextView)
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        val startTimeTextView: TextView = itemView.findViewById(R.id.startTimeTextView)
        val endTimeTextView: TextView = itemView.findViewById(R.id.endTimeTextView)
        val goalsTextView: TextView = itemView.findViewById(R.id.goalsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_timesheet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timesheet = timesheetList[position]
        holder.timesheetNameTextView.text = timesheet.timesheetname
        holder.categoryTextView.text = timesheet.categoryTS
        holder.startTimeTextView.text = timesheet.StartTime
        holder.endTimeTextView.text = timesheet.EndTime
        holder.goalsTextView.text = "Min: ${timesheet.mingoalss}, Max: ${timesheet.maxgoalss}"
    }

    override fun getItemCount(): Int {
        return timesheetList.size
    }
}
