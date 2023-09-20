import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.dataclass.Hit
import com.example.myapplication.interface_class.itemInterface
import java.text.SimpleDateFormat
import java.util.Calendar

class SearchResultsAdapter(private val dataSet: List<Hit> ,private val itemInterface: itemInterface) :
    RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    // Define a ViewHolder to hold your item views
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsview:LinearLayout=view.findViewById(R.id.newsview)
        val titleTextView: TextView = view.findViewById(R.id.tittleTextView)
        val pointsTextView: TextView = view.findViewById(R.id.pointTextView)
        val authorTextView: TextView = view.findViewById(R.id.authorTextView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val commentTextView: TextView = view.findViewById(R.id.commentTextView)



    }
    private val filteredDataSet = dataSet.filter { !it.title.isNullOrBlank() }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = filteredDataSet[position]

        holder.titleTextView.text = item.title
        holder.pointsTextView.text = "${item.points} Points"
        holder.authorTextView.text=item.author
        holder.commentTextView.text="${item.num_comments} Comments"


        val dateString = item.created_at
        val yearsAgo = calculateYearsAgo(dateString)
        if (yearsAgo >= 1) {
            holder.dateTextView.text="${yearsAgo} Year Ago"
        } else {
            val monthAgo=calculateMonthsAgo(dateString)
            holder.dateTextView.text="${monthAgo} Month Ago"
        }

        holder.newsview.setOnClickListener(){
            println()
            itemInterface.getitem(item.objectID)


        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return filteredDataSet.size
    }
}

fun calculateYearsAgo(dateString: String): Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = Calendar.getInstance().time

    try {
        val parsedDate = dateFormat.parse(dateString)

        // Calculate the difference in milliseconds between the two dates
        val diffInMillis = currentDate.time - parsedDate.time

        // Convert milliseconds to years
        val years = (diffInMillis / (1000 * 60 * 60 * 24 * 365.25)).toInt()


        return years
    } catch (e: Exception) {
        e.printStackTrace()
        return -1 // Return -1 if there's an error parsing the date
    }

}
fun calculateMonthsAgo(dateString: String): Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = Calendar.getInstance().time

    try {
        val parsedDate = dateFormat.parse(dateString)

        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = currentDate

        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = parsedDate

        // Calculate the difference in years and months
        val yearsDiff = currentCalendar.get(Calendar.YEAR) - dateCalendar.get(Calendar.YEAR)
        val monthsDiff = currentCalendar.get(Calendar.MONTH) - dateCalendar.get(Calendar.MONTH)

        // Calculate the total months difference
        val totalMonthsDiff = yearsDiff * 12 + monthsDiff

        return totalMonthsDiff
    } catch (e: Exception) {
        e.printStackTrace()
        return -1 // Return -1 if there's an error parsing the date
    }
}

