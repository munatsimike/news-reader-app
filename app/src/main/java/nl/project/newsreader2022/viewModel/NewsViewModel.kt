package nl.project.newsreader2022.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.project.newsreader2022.repository.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repo: NewsRepository
) : ViewModel() {

    val articles = repo.articles
    val nextId = repo.nextId

    init {
        viewModelScope.launch {
            repo.refreshArticles()
        }
    }

    fun getMoreArticles(id: Int, numOfArticles: Int) {
        viewModelScope.launch {
            repo.getMoreArticles(id, numOfArticles)
        }
    }
}
