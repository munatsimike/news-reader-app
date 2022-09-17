package nl.project.newsreader2022.miscellaneous

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import nl.project.newsreader2022.R

// convert bool to int
fun Boolean.toInt() = if (this) 1 else 0

fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, 7000)

    val snackText = snackbar.view.findViewById<TextView>(
        com.google.android.material.R.id.snackbar_text
    )
    snackText.textSize = 18F
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.navy))
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
        requireView().snackBar("No Internet, please check your internet connection.", retry)
    }.onError {
        if (statusCode.code == 401)
            requireView().snackBar(
                "User not logged in, please login to save and access favourites"
            )
    }
}

fun Fragment.popBackStack() {
    Navigation.findNavController(requireView()).popBackStack()
}



