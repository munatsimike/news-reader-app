package nl.project.newsreader2022.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skydoves.sandwich.ApiResponse
import nl.project.newsreader2022.model.MyNetworkResponse

abstract class BaseRepository {
    // for network failures and errors
    protected val toastData_: MutableLiveData<ApiResponse<Any>> = MutableLiveData()
    val toastData: LiveData<ApiResponse<Any>> = toastData_
}