package nl.project.newsreader2022.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import coil.load
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
) {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun ImageView.loadImage(url: String) {
            if (url.isNotEmpty())
                this.load(url)
        }
    }
}


// total articles to retrieve from the Api
fun numOfArticles() = 20