package com.example.myapplication.interface_class

import retrofit2.Call
import retrofit2.http.GET
import com.example.myapplication.dataclass.Story
import com.example.myapplication.dataclass.itemdetails
import retrofit2.http.Path
import retrofit2.http.Query

interface getnews {
    @GET("/api/v1/search?query")
    fun getnews(): Call<Story>

    @GET("/api/v1/search?query=")
    fun getspecificNews(
        @Query("query") query: String
    ):Call<Story>

    @GET("/api/v1/items/{id}")
    fun getItemdata(
        @Path("id") id: Int

    ): Call<itemdetails>
}