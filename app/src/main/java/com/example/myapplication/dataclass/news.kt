package com.example.myapplication.dataclass


data class Story(
    val hits: List<Hit>,
    val nbHits: Int,
    val page: Int,
    val nbPages: Int,
    val exhaustiveNbHits: Boolean,
    val exhaustiveTypo: Boolean,
    val exhaustive: Exhausive,
    val query: String,
    val params: String,
    val processingTimeMS: Int,
    val processingTimingsMS: ProcessingTimingsMS,
    val serverTimeMS: Int
)

data class Hit(
    val created_at: String,
    val title: String?,
    val url: String?,
    val author: String,
    val points: Int,
    val story_text: String?,
    val comment_text: String?,
    val num_comments: Int?,
    val story_id: Int?,
    val story_title: String?,
    val story_url: String?,
    val parent_id: Int?,
    val created_at_i: Long,
    val relevancy_score: Int?,
    val _tags: List<String>,
    val objectID: String,
    val _highlightResult: HighlightResult
)

data class HighlightResult(
    val title: Title,
    val url: Url,
    val author: Author,
    val story_text: StoryText?
)

data class Title(
    val value: String,
    val matchLevel: String,
    val matchedWords: List<String>
)

data class Url(
    val value: String,
    val matchLevel: String,
    val matchedWords: List<String>
)

data class Author(
    val value: String,
    val matchLevel: String,
    val matchedWords: List<String>
)

data class StoryText(
    val value: String,
    val matchLevel: String,
    val matchedWords: List<String>
)

data class Exhausive(
    val nbHits: Boolean,
    val typo: Boolean
)

data class ProcessingTimingsMS(
    val _request: Request
)

data class Request(
    val roundTrip: Int
)
