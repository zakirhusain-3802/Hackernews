package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.CommentsAdapter
import com.example.myapplication.dataclass.Comment
import com.example.myapplication.dataclass.itemdetails
import com.example.myapplication.interface_class.getnews
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostDetails : AppCompatActivity() {
    private val QBase_url1="https://hn.algolia.com"
    private val commnets =ArrayList<Comment>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        // Showing progress bar until the data is fetched

        val layout:LinearLayout=findViewById(R.id.postdetails)
        layout.visibility=View.INVISIBLE
        val progreesbar:ProgressBar=findViewById(R.id.progressBar)
        progreesbar.visibility=View.VISIBLE

//Getting object id from the main activity
        val objectid=intent.getStringExtra("objid")
        val obj= objectid?.toInt()
// Getting data of the news from the api callig using obj id
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(QBase_url1)
            .build()
            .create(getnews::class.java)
        val retfofitData =
            retrofitBuilder.getItemdata(obj!!)
        retfofitData.enqueue(object : Callback<itemdetails?>{

            override fun onResponse(call: Call<itemdetails?>, response: Response<itemdetails?>) =
                if (response.isSuccessful){
                    layout.visibility=View.VISIBLE
                    progreesbar.visibility=View.GONE

                    // if respose is successfull the attach to the view

                    val responseBody = response.body()
                    val title=findViewById<TextView>(R.id.postTitle)
                    val point=findViewById<TextView>(R.id.pointTextViewPost)
                    val author=findViewById<TextView>(R.id.authorTextViewPost)
                    val url:TextView=findViewById(R.id.posturl)
                    val commnets=responseBody?.children
                    title.text=responseBody?.title.toString()
                    point.text=responseBody?.points.toString()+" Points"
                    author.text=responseBody?.author.toString()
                    if(responseBody?.url!=null){
                    url.text=responseBody?.url}
                    else{
                        url.visibility=View.GONE
                    }

                    val recyclerView: RecyclerView = findViewById(R.id.postrecycle)
                    val layoutManager = LinearLayoutManager(this@PostDetails)
                    recyclerView.layoutManager = layoutManager
                    val adapter = commnets?.let { CommentsAdapter(this@PostDetails, it) }
                    recyclerView.adapter = adapter



                } else {
                    // is response is successful then printing the error in logcat for tackle erroe
                    println(response)

                }


            override fun onFailure(call: Call<itemdetails?>, t: Throwable) {
               // on failure if data not received going back  ask to check internet connectivity
                Toast.makeText(applicationContext," Check Internet Connectivty ",Toast.LENGTH_LONG).show()
                onBackPressed()
                println(t.message.toString())
            }
        } )





    }
}