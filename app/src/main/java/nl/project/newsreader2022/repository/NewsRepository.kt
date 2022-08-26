package nl.project.newsreader2022.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.network.NewsApi
import javax.inject.Inject


class NewsRepository @Inject constructor() {
    private val _error = MutableLiveData<String>()
    private val _articles = MutableLiveData<List<NewsArticle>>()

    val articles: LiveData<List<NewsArticle>>
        get() = _articles
    val error: LiveData<String>
        get() = _error

    suspend fun getNewsArticles() {
        withContext(Dispatchers.Main) {
            val getArticle = NewsApi.retrofitService.getNewsAsync().await()
            try {
                _articles.value = getArticle.Results
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
