package nl.project.newsreader2022.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import nl.project.newsreader2022.repository.UserRepository
import nl.project.newsreader2022.utils.Coroutines
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _passwordError = MutableLiveData("")
    private val _usernameError = MutableLiveData("")

    val passwordError: LiveData<String> = _passwordError
    val usernameError: LiveData<String> = _usernameError

    val authToken = userRepository.authToken

    fun validateUsername(username: String) {
        if (username.length < 8) {
            _usernameError.value = "Minimum 8 Character Password"
        } else if (!username.matches("^[0-9a-zA-Z]+$".toRegex())) {
            _usernameError.value = "Must contain letters and numbers only"
        } else {
            _usernameError.value = null
        }
    }

    fun validatePassword(passwordText: String) {
        if (passwordText.length < 8) {
            _passwordError.value = "Minimum 8 Character Password"
        } else if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            _passwordError.value = "Must Contain 1 Upper-case Character"
        } else if (!passwordText.matches(".*[a-z].*".toRegex())) {
            _passwordError.value = "Must Contain 1 Lower-case Character"
        } else if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            _passwordError.value = "Must Contain 1 Special Character (@#\$%^&+=)"
        } else {
            _passwordError.value = null
        }
    }

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            if (passwordError.value == null && usernameError.value == null) {
                userRepository.userRegister(username, password).toString()
            }
        }
    }

    fun userLogin(
        username: String, password: String
    ) {
        Log.d("saved", "Logging in outside scope")
        viewModelScope.launch {
            Log.d("saved", "Logging in inside scope")
            userRepository.userLogin(username, password).toString()
        }
    }

    fun logout() {
        Log.d("saved", "Loggin out outside scope")
        viewModelScope.launch {
            Log.d("saved", "Logging out inside scope")
            userRepository.logout()
        }
    }

}