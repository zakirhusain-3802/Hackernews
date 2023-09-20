package com.example.myapplication.dataclass

data class Comment(
    val id: Int,
    val created_at: String,
    val created_at_i: Long,
    val type: String,
    val author: String,
    val title: String?,
    val url: String?,
    val text: String,
    val points: Int?,
    val parent_id: Int,
    val story_id: Int,
    val children: List<Comment>,
    val options: List<Any>
)

data class itemdetails(
    val id: Int,
    val created_at: String,
    val created_at_i: Long,
    val type: String,
    val author: String,
    val title: String,
    val url: String,
    val text: String?,
    val points: Int,
    val parent_id: Int?,
    val story_id: Int?,
    val children: List<Comment>,
    val options: List<Any>
)
