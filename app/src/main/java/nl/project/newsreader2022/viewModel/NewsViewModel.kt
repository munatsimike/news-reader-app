package nl.project.newsreader2022.viewModel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.repository.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repo: NewsRepository
) : BaseViewModel<NewsRepository>(repo) {

    val likedArticles = repo.likedArticle
    val articles = repo.articles
    val nextId = repo.nextId
    val isUserLoggedIn = repo.isUserLoggedIn

    init {
        refreshArticles()
    }

    // get more articles after previous batch has scrolled to the end on the recycler view
    fun getMoreArticles(id: Int, numOfArticles: Int) {
        viewModelScope.launch {
            repo.getMoreArticles(id, numOfArticles)
        }
    }

    //
    fun likeDislike(article: NewsArticle) {
        viewModelScope.launch {
            repo.likeDislikeAPi(article)
        }
    }
    
   fun refreshLikedArticles() {
       viewModelScope.launch {
           repo.likedArticles()
       }
    }

    fun refreshArticles(){
        viewModelScope.launch {
            repo.refreshArticles()
        }
    }
}
