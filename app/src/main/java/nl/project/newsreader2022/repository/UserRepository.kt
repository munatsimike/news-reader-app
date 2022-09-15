package nl.project.newsreader2022.repository

import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.project.newsreader2022.network.UserApi
import nl.project.newsreader2022.miscellaneous.Coroutines
import nl.project.newsreader2022.miscellaneous.Event
import nl.project.newsreader2022.model.Token
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userManager: UserManager
) : BaseRepository() {
    val authToken = userManager.getAuthToken

    suspend fun userLogin(
        username: String,
        password: String
    ) {
        UserApi.retrofitService.userLoginAsync(username, password)
            .onSuccess {
                Coroutines.io { saveAuthToken(data) }
            }
            .onError { toastData_.value = Event(this) }
            .onFailure { toastData_.value = Event(this) }
    }

    suspend fun userRegister(
        username: String,
        password: String
    ) {
        UserApi.retrofitService.registerUserAsync(username, password)
            .onSuccess { toastData_.value = Event(this) }
            .onError { toastData_.value = Event(this) }
            .onFailure { toastData_.value = Event(this) }
    }

    // save authtoken to dataStore
    private suspend fun saveAuthToken(token: Token) {
        withContext(Dispatchers.IO) {
            userManager.saveAuthToken(token)
        }
    }

    suspend fun logout() {
        userManager.deleteAuthToken()
    }
}