package com.example.myapplication

import SearchResultsAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.dataclass.Hit
import com.example.myapplication.dataclass.Story
import com.example.myapplication.interface_class.getnews
import com.example.myapplication.interface_class.itemInterface
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

const val QBase_url="https://hn.algolia.com"

class MainActivity : AppCompatActivity(), itemInterface {
    private lateinit var recyclerView: RecyclerView
    private val datain = ArrayList<Hit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.re_news)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //we get fist some news from he api by calling it

        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(QBase_url)
            .build()
            .create(getnews::class.java)
        val retfofitData =
            retrofitBuilder.getnews()
        retfofitData.enqueue(object : Callback<Story?>{
            override fun onResponse(call: Call<Story?>, response: Response<Story?>) =
                if (response.isSuccessful)
                {
                    val responseBody = response.body()
                    val news=responseBody?.hits
                    for(data in news!!){
                        val new=Hit(data.created_at,data.title,data.url,data.author,data.points,data.story_text,data.comment_text
                        ,data.num_comments,data.story_id,data.story_title,data.story_url,data.parent_id,data.created_at_i,data.relevancy_score
                        ,data._tags,data.objectID,data._highlightResult)
                        datain.add(new)
                        println(new)

                    }
//                    println(datain)
                   val  adapter = SearchResultsAdapter(datain,this@MainActivity)
                    recyclerView.adapter = adapter

                } else {
                    // is response is successful then printing the error in logcat for tackle erroe
                    println(response)
                }
            override fun onFailure(call: Call<Story?>, t: Throwable) {
                // on failure if data not received going back  ask to check internet connectivity
                Toast.makeText(applicationContext," Check Internet Connectivity ",Toast.LENGTH_LONG).show()
                println(t.message.toString())
            }
        } )

       val  textInputEditText = findViewById<TextInputEditText>(R.id.se_news)

        // Add a TextWatcher to listen for text changes
        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text changes
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called when the text changes
                val query = s.toString().trim() // Get the entered text
                if (query.isNotEmpty()) {
                    // Trigger the search function when the user enters text
                    performSearch(query)
                } else {

                }
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text changes
                // but we are not using it
            }
        })


    }

    private fun performSearch(query: String) {

        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(QBase_url)
            .build()
            .create(getnews::class.java)
        val retfofitData =
            retrofitBuilder.getspecificNews(query)
        retfofitData.enqueue(object : Callback<Story?>{
            override fun onResponse(call: Call<Story?>, response: Response<Story?>) =
                if (response.isSuccessful)
                {
                    datain.clear()
                    val responseBody = response.body()

                    val news=responseBody?.hits

                    for(data in news!!){
                        val new=Hit(data.created_at,data.title,data.url,data.author,data.points,data.story_text,data.comment_text
                            ,data.num_comments,data.story_id,data.story_title,data.story_url,data.parent_id,data.created_at_i,data.relevancy_score
                            ,data._tags,data.objectID,data._highlightResult)
                        datain.add(new)
                        println(new)

                    }
//                    println(datain)
                    val  adapter = SearchResultsAdapter(datain,this@MainActivity)
                    recyclerView.adapter = adapter

                } else {
                    // is response is successful then printing the error in logcat for tackle erroe
                    println(response)
                }
            override fun onFailure(call: Call<Story?>, t: Throwable) {
                // on failure if data not received going back  ask to check internet connectivity
                Toast.makeText(applicationContext," Check Internet Connectivty ",Toast.LENGTH_LONG).show()
                println(t.message.toString())

            }
        } )
    }

    override fun getitem(objectID: String) {
    println(" Click on this object  ${objectID} ")
        val i =Intent(this,PostDetails::class.java)
        i.putExtra("objid",objectID)
        startActivity(i)

    }
}