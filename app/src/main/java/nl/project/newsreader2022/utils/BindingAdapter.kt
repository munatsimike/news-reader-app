package nl.project.newsreader2022.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}

@BindingAdapter("android:onClick", "android:clickable")
fun setOnClick(
    view: View, clickListener: View.OnClickListener?,
    clickable: Boolean
) {
    view.setOnClickListener(clickListener)
    view.isClickable = clickable
}

