package nl.project.newsreader2022.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import nl.project.newsreader2022.model.MyData

abstract class BaseRepository {
    // for network failures and errors
    private val toastData_: MutableLiveData<String> = MutableLiveData()
    val toastData: LiveData<String> = toastData_

    fun handleAPiErrorFailure(response: ApiResponse<MyData>){
        response.onFailure {
            toastData_.value = message()
        }.onError {
            toastData_.value = statusCode.code.toString() + " " + errorBody
        }
    }
}