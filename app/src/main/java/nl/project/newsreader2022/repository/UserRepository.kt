package nl.project.newsreader2022.repository

import android.util.Log
import nl.project.newsreader2022.model.AuthToken
import nl.project.newsreader2022.network.UserApi
import nl.project.newsreader2022.utils.Coroutines
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userManager: UserManager
) {

    val authToken = userManager.getAuthToken

     fun userLogin(
        username: String,
        password: String
    ) = Coroutines.io {
        val token = UserApi.retrofitService.userLoginAsync(username, password).await()
        saveAuthToken(token)
    }

    suspend fun userRegister(
        username: String,
        password: String
    ) = Coroutines.io {
        UserApi.retrofitService.registerUserAsync(username, password).await()
    }

    // save authtoken to dataStore
    private suspend fun saveAuthToken(token: AuthToken) {
        userManager.saveAuthToken(token)
    }

    suspend fun logout() {
        userManager.deleteAuthToken()
    }
}