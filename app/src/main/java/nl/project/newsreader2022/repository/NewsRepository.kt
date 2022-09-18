package nl.project.newsreader2022.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.project.newsreader2022.database.ArticleDB
import nl.project.newsreader2022.model.MyData
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.network.NewsApi
import nl.project.newsreader2022.miscellaneous.Coroutines
import nl.project.newsreader2022.miscellaneous.Event
import nl.project.newsreader2022.miscellaneous.toInt
import javax.inject.Inject

class NewsRepository @Inject constructor(private val database: ArticleDB) : BaseRepository() {

    // observe data in local DB
    val articles: LiveData<List<NewsArticle>> = database.newsDao.getAllArticles()
    val nextId: LiveData<Int> = database.nextIdDao.fetchNextId()

    // store liked articles
    private val _likedArticle: MutableLiveData<List<NewsArticle>> = MutableLiveData()
    val likedArticle: LiveData<List<NewsArticle>> = _likedArticle

    // user is logged in marker
    private val userLoggedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    val isUserLoggedIn: LiveData<Boolean> = userLoggedIn

    // get first batch of articles
    suspend fun refreshArticles() {
        deleteArticleNextId()
        handleAPiCalls(NewsApi.retrofitService.getInitArticlesAsync())
    }

    // get more articles  after initial batch
    suspend fun getMoreArticles(nextId: Int, numOfArticles: Int) {
        handleAPiCalls(NewsApi.retrofitService.getMoreArticlesAsync(nextId, numOfArticles))
    }

    // handle response from api calls
    private suspend fun handleAPiCalls(networkResponse: ApiResponse<MyData>) {
        networkResponse
            .onSuccess {
                Coroutines.io { saveArticlesNextIdToRoom(data) }
            }.onFailure {
                toastData_.value = Event(this)
            }.onError {
                toastData_.value = Event(this)
            }
    }

    private suspend fun saveArticlesNextIdToRoom(data: MyData) {
        saveArticlesToDatabase(data.Results)
        saveNextIdToDatabase(data.NextId)
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
                .onSuccess {
                    // if article can be like user is logged in
                    userLoggedIn.value = true
                    Coroutines.io {
                        // update local database
                        likeDislikeRoomDB(!article.IsLiked, article.Id)
                    }

                    // update liked articles remote server
                    Coroutines.main {
                        likedArticles()
                    }
                }
                .onError { toastData_.value = Event(this) }
                .onFailure { toastData_.value = Event(this) }

        } else {
            // dislike article
            NewsApi.retrofitService.disLikeArticleAsync(article.Id)
                .onSuccess {
                    Coroutines.io {
                        // update local database
                        likeDislikeRoomDB(!article.IsLiked, article.Id)
                    }

                    // update liked articles remote server
                    Coroutines.main {
                        likedArticles()
                    }
                }
                .onError { toastData_.value = Event(this) }
                .onFailure { toastData_.value = Event(this) }
        }

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
        NewsApi.retrofitService.likedArticlesAsync()
            .onSuccess { _likedArticle.value = data.Results }
            .onError { toastData_.value = Event(this) }
            .onFailure { toastData_.value = Event(this) }
    }

    private suspend fun deleteArticleNextId() {
        withContext(Dispatchers.IO) {
            database.newsDao.deleteAllArticles()
            database.nextIdDao.deleteAllIds()
        }
    }
}
