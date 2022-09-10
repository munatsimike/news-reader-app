package nl.project.newsreader2022.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.project.newsreader2022.model.enums.LoginRegisterBtn
import nl.project.newsreader2022.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel<UserRepository>(userRepository) {
    // get authtoken from preference
    val authToken = userRepository.authToken

    // store username validation error messages
    private val _passwordError = MutableLiveData("")
    private val _usernameError = MutableLiveData("")

    val passwordError: LiveData<String> = _passwordError
    val usernameError: LiveData<String> = _usernameError

    // text message to be displayed on the login in button: Login or Register
    private val _loginRegisterBtnText: MutableLiveData<String> =
        MutableLiveData(LoginRegisterBtn.LOGIN.text)
    val loginRegisterBtnText: LiveData<String> = _loginRegisterBtnText

    // message that prompts the user to register or login
    private val _registerLoginTextToggle: MutableLiveData<Boolean?> = MutableLiveData()
    val registerLoginTextToggle: LiveData<Boolean?> = _registerLoginTextToggle

    fun setLoginRegisterBtnText(loginRegisterBtnState: Boolean) {
        if (loginRegisterBtnState) {
            _loginRegisterBtnText.value = LoginRegisterBtn.REGISTER.text
        } else {
            _loginRegisterBtnText.value = LoginRegisterBtn.LOGIN.text
        }
        // clear value so that if there are duplicate values livedata will notify observers
        _registerLoginTextToggle.value = null
        // set toggle button text
        _registerLoginTextToggle.value = loginRegisterBtnState
    }

    // validate username on login or registration
    fun validateUsername(username: String) {
        if (username.length < 8) {
            _usernameError.value = "Minimum 8 Character Password"
        } else if (!username.matches("^[0-9a-zA-Z]+$".toRegex())) {
            _usernameError.value = "Must contain letters and numbers only"
        } else {
            _usernameError.value = null
        }
    }

    // validate password on login or user registration
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

    // login or a register user
    fun userLoginRegister(
        username: String, password: String
    ) {
        if (passwordError.value == null && usernameError.value == null) {
            viewModelScope.launch {
                if (loginRegisterBtnText.value == LoginRegisterBtn.LOGIN.text) {
                    userRepository.userLogin(username, password).toString()
                } else {
                    userRepository.userRegister(username, password).toString()
                }
            }
        }
    }

    // logout current user
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}