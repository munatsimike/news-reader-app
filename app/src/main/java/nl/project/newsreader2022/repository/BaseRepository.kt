package nl.project.newsreader2022.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skydoves.sandwich.ApiResponse
import nl.project.newsreader2022.miscellaneous.Event

abstract class BaseRepository {
    // for network failures, errors or any message to be displayed to the user
    protected val toastData_: MutableLiveData<Event<ApiResponse<Any>>> = MutableLiveData()
    val toastData: LiveData<Event<ApiResponse<Any>>> = toastData_
}