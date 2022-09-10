package nl.project.newsreader2022.repository

import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.project.newsreader2022.model.AuthToken
import nl.project.newsreader2022.network.UserApi
import nl.project.newsreader2022.miscellaneous.Coroutines
import nl.project.newsreader2022.miscellaneous.Event
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
                Coroutines.io { saveAuthToken(AuthToken(data.AuthToken)) }
            }.onError { toastData_.value = Event(this) }
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
    private suspend fun saveAuthToken(token: AuthToken) {
        withContext(Dispatchers.IO) {
            userManager.saveAuthToken(token)
        }
    }

    suspend fun logout() {
        userManager.deleteAuthToken()
    }
}