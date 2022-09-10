package nl.project.newsreader2022.miscellaneous

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure

fun Boolean.toInt() = if (this) 1 else 0

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, 7000)
    val snackText = snackbar.view.findViewById<TextView>(
        com.google.android.material.R.id.snackbar_text
    )
    snackText.textSize = 18F
    snackbar.setBackgroundTint(Color.rgb(55,0,179))
    snackbar.setTextMaxLines(3)

    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

// handle errors in wrapped in the resource class
fun Fragment.showApiErrorFailure(
    response: ApiResponse<Any>,
    retry: (() -> Unit)? = null // try if operation fails
) {

    response.onFailure {
        requireView().snackbar("No Internet, please check your internet connection")
    }.onError {
        if (statusCode.code == 401)
            requireView().snackbar(
                "User not logged in, please login to access favourites"
            )
    }
}

