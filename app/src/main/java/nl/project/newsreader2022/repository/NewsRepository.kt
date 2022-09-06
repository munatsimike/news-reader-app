package nl.project.newsreader2022.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.project.newsreader2022.database.ArticleDB
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.network.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(private val database: ArticleDB) {
    val articles: LiveData<List<NewsArticle>> = database.newsDao.getAllArticles()
    val nextId: LiveData<Int> = database.nextIdDao.fetchNextId()

    suspend fun refreshArticles() {
        withContext(Dispatchers.IO) {
            val response = NewsApi.retrofitService.getInitArticlesAsync().await()
            database.newsDao.deleteAllArticles()
            database.nextIdDao.deleteAllIds()
            saveArticlesToDatabase(response.Results)
            saveNextIdToDatabase(response.NextId)
        }
    }

    suspend fun getMoreArticles(nextId: Int, numOfArticles: Int) {
        withContext(Dispatchers.IO) {
            val response =
                NewsApi.retrofitService.getMoreArticlesAsync(nextId, numOfArticles).await()
            saveArticlesToDatabase(response.Results)
            saveNextIdToDatabase(response.NextId)
        }
    }

    private suspend fun saveArticlesToDatabase(articles: List<NewsArticle>) {
        database.newsDao.insertAll(articles)
    }

    private suspend fun saveNextIdToDatabase(nextId: Int) {
        database.nextIdDao.insertNextId(nextId)
    }

    suspend fun likeDislike(article: NewsArticle) {
        if (!article.IsLiked) {
            NewsApi.retrofitService.likeArticleAsync(article.Id).await()
        } else {
            NewsApi.retrofitService.disLikeArticleAsync(article.Id).await()

        }
    }
}

