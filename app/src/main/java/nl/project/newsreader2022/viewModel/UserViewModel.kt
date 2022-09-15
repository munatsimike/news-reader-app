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

    // button text register or login
    private val _loginRegisterBtnText: MutableLiveData<String> =
        MutableLiveData(LoginRegisterBtn.LOGIN.text)
    val loginRegisterBtnText: LiveData<String> = _loginRegisterBtnText

    // message that prompts the user to register or login
    private val _registerLoginTextToggle: MutableLiveData<Boolean?> = MutableLiveData()
    val registerLoginTextToggle: LiveData<Boolean?> = _registerLoginTextToggle

    // text message to be displayed on the login in button: Login or Register
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

    // login or a register user
    fun userLoginRegister(
        username: String, password: String
    ) {
        viewModelScope.launch {
            if (loginRegisterBtnText.value == LoginRegisterBtn.LOGIN.text) {
                userRepository.userLogin(username, password).toString()
            } else {
                userRepository.userRegister(username, password).toString()
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