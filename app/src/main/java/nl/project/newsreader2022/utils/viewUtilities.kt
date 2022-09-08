package nl.project.newsreader2022.utils

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar

fun Boolean.toInt() = if (this) 1 else 0

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, 9000)
    snackbar.setBackgroundTint(Color.BLACK)
    snackbar.setTextMaxLines(3)

    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

