package nl.project.newsreader2022.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess

fun Boolean.toInt() = if (this) 1 else 0

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, 9000)
    val snackText = snackbar.view.findViewById<TextView>(
        com.google.android.material.R.id.snackbar_text)
    snackText.textSize=18F
    snackbar.setBackgroundTint(Color.DKGRAY)
    snackbar.setTextMaxLines(3)

    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

// handle errors in wrapped in the resource class
fun Fragment.handleApiError(
    response: ApiResponse<Any>,
    retry: (() -> Unit)? = null // try if operation fails
) {
    response.onFailure {
        requireView().snackbar(
            "No Internet, please check your internet connection",
        )
    }.onError {
        if (statusCode.code == 401)
        requireView().snackbar(
            "Invalid credentials, please check your username and password"
        )
    }.onSuccess {

    }
}
