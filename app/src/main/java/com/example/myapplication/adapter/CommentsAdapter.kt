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

class CommentsAdapter(private val context: Context, private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    // Implement the required methods for RecyclerView.Adapter

     class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define and initialize views for the comment item
        val title:TextView=itemView.findViewById(R.id.postcommnet)
         val recyclview:RecyclerView=itemView.findViewById(R.id.nestedrecyle)
         val auth:TextView=itemView.findViewById(R.id.commnetauth)
         val layout:LinearLayout=itemView.findViewById(R.id.commentlayout)
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

        val decodedData = decodeHtmlEntities(rawData)
        holder.title.text=decodedData}
        if(comment.children!=null){
            val childcommnets=comment.children
            val layoutManager = LinearLayoutManager(context)
            holder.recyclview.layoutManager = layoutManager
            val adapter = CommentsAdapter(context,childcommnets)
            holder.recyclview.adapter=adapter
        }
        else{
            holder.layout.visibility=View.GONE
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
