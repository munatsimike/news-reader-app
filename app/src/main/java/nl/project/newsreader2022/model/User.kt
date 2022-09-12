package nl.project.newsreader2022.model

import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

data class User(val username: String, val password: String) {

    companion object {
        // validate username on login or registration
        @JvmStatic
        @BindingAdapter("app:isUsernameValid")
        fun TextInputEditText.validateUsername(_usernameError: TextInputLayout) {
            this.doOnTextChanged { text, _, _, _ ->
                if (text != null) {
                    if (text.length < 8) {
                        _usernameError.helperText = "Minimum 8 Character Password"
                    } else if (!text.matches("^[0-9a-zA-Z]+$".toRegex())) {
                        _usernameError.helperText = "Must contain letters and numbers only"
                    } else {
                        _usernameError.helperText = null
                    }
                }
            }
        }

        // validate password on login or user registration
        @JvmStatic
        @BindingAdapter("app:isPassValid")
        fun TextInputEditText.validatePassword(_passwordError: TextInputLayout) {
            this.doOnTextChanged { password, _, _, _ ->
                if (password != null) {
                    if (password.length < 8) {
                        _passwordError.helperText = "Minimum 8 Character Password"
                    } else if (!password.matches(".*[A-Z].*".toRegex())) {
                        _passwordError.helperText = "Must Contain 1 Upper-case Character"
                    } else if (!password.matches(".*[a-z].*".toRegex())) {
                        _passwordError.helperText = "Must Contain 1 Lower-case Character"
                    } else if (!password.matches(".*[@#\$%^&+=].*".toRegex())) {
                        _passwordError.helperText = "Must Contain 1 Special Character (@#\$%^&+=)"
                    } else {
                        _passwordError.helperText = null
                    }
                }
            }
        }
    }
}