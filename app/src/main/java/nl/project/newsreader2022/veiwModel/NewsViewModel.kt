package nl.project.newsreader2022.veiwModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import nl.project.newsreader2022.repository.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repo: NewsRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val articles = repo.articles
    val error = repo.error

    init {
        coroutineScope.launch {
            repo.refreshArticles()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}