package nl.project.newsreader2022.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Json(name = "Result")
@Entity(tableName = "article")
data class NewsArticle(
    @PrimaryKey
    val Id: Int,
    val Categories: List<Category>,
    val Feed: Int,
    val Image: String,
    val IsLiked: Boolean,
    val PublishDate: String,
    val Related: List<String>,
    val Summary: String,
    val Title: String,
    val Url: String
)