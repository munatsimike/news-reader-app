package nl.project.newsreader2022.model

import com.squareup.moshi.Json

@Json(name = "Result")
data class NewsArticle(
    val Categories: List<Category>,
    val Feed: Int,
    val Id: Int,
    val Image: String,
    val IsLiked: Boolean,
    val PublishDate: String,
    val Related: List<String>,
    val Summary: String,
    val Title: String,
    val Url: String
)