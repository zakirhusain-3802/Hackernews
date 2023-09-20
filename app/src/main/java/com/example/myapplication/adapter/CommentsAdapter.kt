package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.dataclass.Comment
import java.text.SimpleDateFormat
import java.util.Calendar

class CommentsAdapter(private val context: Context, private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    // Implement the required methods for RecyclerView.Adapter

     class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define and initialize views for the comment item
        val title:TextView=itemView.findViewById(R.id.postcommnet)
         val recyclview:RecyclerView=itemView.findViewById(R.id.nestedrecyle)
         val auth:TextView=itemView.findViewById(R.id.commnetauth)
         val date:TextView=itemView.findViewById(R.id.commnetdate)
    }
    // removing empty comments
    private val filteredDataSet = comments.filter { !it.text.isNullOrBlank() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        // Inflate the comment item layout and return a ViewHolder
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.comments, parent, false)
        return CommentViewHolder(view)

    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        // Bind data to the cocomment item views, including handling nested comments recursively
        val comment = filteredDataSet[position]
        println(comment)
        if (comment.text!=null){

        val rawData = comment.text
            holder.auth.text=comment.author
            val yearsAgo = calculateYearsAgo(comment.created_at)
            if (yearsAgo >= 1) {
                holder.date.text="${yearsAgo} Year Ago"
            } else {
                val monthAgo=calculateMonthsAgo(comment.created_at)
                holder.date.text="${monthAgo} Month Ago"
            }

        val decodedData = decodeHtmlEntities(rawData)
        holder.title.text=decodedData


        }
        if(comment.children!=null){
            val childcommnets=comment.children
            val layoutManager = LinearLayoutManager(context)
            holder.recyclview.layoutManager = layoutManager
            val adapter = CommentsAdapter(context,childcommnets)
            holder.recyclview.adapter=adapter
        }





    }

    override fun getItemCount(): Int {
        // Return the total number of comments
        return filteredDataSet.size
    }
    fun decodeHtmlEntities(input: String): String {
      //  converting raw data into simple text
        val decodedString = HtmlCompat.fromHtml(input, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
        return decodedString.replace(Regex("<.*?>"), "")
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
    }}
