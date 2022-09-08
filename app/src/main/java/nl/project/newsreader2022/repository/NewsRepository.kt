package nl.project.newsreader2022.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.project.newsreader2022.database.ArticleDB
import nl.project.newsreader2022.model.MyData
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.network.NewsApi
import nl.project.newsreader2022.utils.Coroutines
import nl.project.newsreader2022.utils.toInt
import retrofit2.Call
import javax.inject.Inject

class NewsRepository @Inject constructor(private val database: ArticleDB) : BaseRepository() {

    // observe data in local DB
    val articles: LiveData<List<NewsArticle>> = database.newsDao.getAllArticles()
    val nextId: LiveData<Int> = database.nextIdDao.fetchNextId()

    // store liked articles
    private val _likedArticle: MutableLiveData<List<NewsArticle>> = MutableLiveData()
    val likedArticle: LiveData<List<NewsArticle>> = _likedArticle

    // get first batch of articles
    suspend fun refreshArticles() {
        Coroutines.io {
            deleteArticleNextId()
        }
        handleAPiCalls { NewsApi.retrofitService.getInitArticlesAsync() }
    }

    // get more articles  after initial batch
    suspend fun getMoreArticles(nextId: Int, numOfArticles: Int) {
        handleAPiCalls { NewsApi.retrofitService.getMoreArticlesAsync(nextId, numOfArticles) }
    }

    // handle response from api calls
    private suspend fun handleAPiCalls(networkResponse: () -> Call<MyData>) {
        networkResponse.invoke()
            .request { response ->
                response.onSuccess {
                    Coroutines.io {
                        saveArticlesNextIdToRoom(data)
                    }
                }.onFailure {
                    handleAPiErrorFailure(response)
                }.onError {
                    handleAPiErrorFailure(response)
                }
            }.request()
    }

    private suspend fun saveArticlesNextIdToRoom(data: MyData) {
        withContext(Dispatchers.IO) {
            saveArticlesToDatabase(data.Results)
            saveNextIdToDatabase(data.NextId)
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
    private suspend fun likeDislikeRoomDB(isLike: Boolean, id: Int) {
        if (isLike) {
            // like article
            database.newsDao.likeDislike(isLike.toInt(), id)
        } else {
            // dislike article
            database.newsDao.likeDislike(isLike.toInt(), id)
        }
    }

    // fetch liked articles from api
    suspend fun likedArticles() {
        withContext(Dispatchers.IO) {
            NewsApi.retrofitService.likedArticlesAsync().request { response ->
                response.onSuccess {
                    _likedArticle.value = data.Results
                }
            }
        }
    }

    private suspend fun deleteArticleNextId() {
        withContext(Dispatchers.IO) {
            database.newsDao.deleteAllArticles()
            database.nextIdDao.deleteAllIds()
        }
    }
}
