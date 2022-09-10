package nl.project.newsreader2022.viewModel

import androidx.lifecycle.ViewModel
import nl.project.newsreader2022.repository.BaseRepository

// base repo contains code to be used by the user and news repository
abstract class BaseViewModel<T: BaseRepository>(repository: BaseRepository): ViewModel()  {
    val toastData = repository.toastData
}