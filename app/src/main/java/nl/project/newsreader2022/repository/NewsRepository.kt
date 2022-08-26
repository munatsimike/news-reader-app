package nl.project.newsreader2022.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.project.newsreader2022.database.ArticleDB
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.network.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(private val database: ArticleDB) {
    private val _error = MutableLiveData<String>()
    val articles: LiveData<List<NewsArticle>> = database.newsDao.getAllArticles()

    val error: LiveData<String>
        get() = _error

    suspend fun refreshArticles() {
        withContext(Dispatchers.IO) {
            val getArticle = NewsApi.retrofitService.getNewsAsync().await()
            database.newsDao.insertAll(getArticle.Results)
        }
    }
}
