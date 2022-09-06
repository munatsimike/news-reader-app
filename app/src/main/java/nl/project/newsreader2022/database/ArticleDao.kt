package nl.project.newsreader2022.database

import androidx.lifecycle.LiveData
import androidx.room.*
import nl.project.newsreader2022.model.NewsArticle

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(article: List<NewsArticle>)

    @Query("SELECT * FROM article ORDER BY Id DESC")
    fun getAllArticles(): LiveData<List<NewsArticle>>

    @Query("DELETE FROM article")
    fun deleteAllArticles()

    @Query("UPDATE article SET IsLiked = :isLike WHERE Id = :id")
    suspend fun likeDislike(isLike: Int, id: Int)
}