package nl.project.newsreader2022.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.project.newsreader2022.model.NewsArticle

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(article: List<NewsArticle>)

    @Query("SELECT * FROM article")
    fun getAllArticles() : LiveData<List<NewsArticle>>
}