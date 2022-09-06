package nl.project.newsreader2022.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nl.project.newsreader2022.database.ArticleDB
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.network.NewsApi
import nl.project.newsreader2022.utils.Coroutines
import nl.project.newsreader2022.utils.toInt
import javax.inject.Inject

class NewsRepository @Inject constructor(private val database: ArticleDB) {
    val articles: LiveData<List<NewsArticle>> = database.newsDao.getAllArticles()
    val nextId: LiveData<Int> = database.nextIdDao.fetchNextId()

    private val _likedArticle: MutableLiveData<List<NewsArticle>> = MutableLiveData()
    val likedArticle: LiveData<List<NewsArticle>> = _likedArticle

    // get first batch of articles
    suspend fun refreshArticles() {
        Coroutines.io {
            val response = NewsApi.retrofitService.getInitArticlesAsync().await()
            database.newsDao.deleteAllArticles()
            database.nextIdDao.deleteAllIds()
            saveArticlesToDatabase(response.Results)
            saveNextIdToDatabase(response.NextId)
        }
    }

    // get more articles  after initial batch
    suspend fun getMoreArticles(nextId: Int, numOfArticles: Int) {
        Coroutines.io {
            val response =
                NewsApi.retrofitService.getMoreArticlesAsync(nextId, numOfArticles).await()
            saveArticlesToDatabase(response.Results)
            saveNextIdToDatabase(response.NextId)
        }
    }

    // save articles from api to local database
    private suspend fun saveArticlesToDatabase(articles: List<NewsArticle>) {
        database.newsDao.insertAll(articles)
    }

    // save next id to local db for endless scrolling
    private suspend fun saveNextIdToDatabase(nextId: Int) {
        database.nextIdDao.insertNextId(nextId)
    }

    // like dislike remote api
    suspend fun likeDislikeAPi(article: NewsArticle) {
        if (!article.IsLiked) {
            // like article
            NewsApi.retrofitService.likeArticleAsync(article.Id)
        } else {
            // dislike article
            NewsApi.retrofitService.disLikeArticleAsync(article.Id)
        }
        // update local database
        likeDislikeRoomDB(!article.IsLiked, article.Id)

        // update liked articles
        likedArticles()
    }

    // like dislike article in local database
    private fun likeDislikeRoomDB(isLike: Boolean, id: Int) {
        Coroutines.io {
            if (isLike) {
                // like article
                database.newsDao.likeDislike(isLike.toInt(), id)
            } else {
                // dislike article
                database.newsDao.likeDislike(isLike.toInt(), id)
            }
        }
    }

    // fetch liked articles from api
    suspend fun likedArticles() {
        val response = NewsApi.retrofitService.likedArticlesAsync().await()
        _likedArticle.value = response.Results
    }
}

