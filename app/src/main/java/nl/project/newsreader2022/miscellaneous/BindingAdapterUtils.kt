package nl.project.newsreader2022.miscellaneous

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import nl.project.newsreader2022.R

class BindingAdapterUtils {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun ImageView.loadImage(url: String) {
            if (url.isNotEmpty())
                this.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_image_24)
                    transformations(RoundedCornersTransformation())
                }
        }

        @JvmStatic
        @BindingAdapter("urlList", "layout")
        fun TextView.showArticles(articles: List<String>, layout: LinearLayout) {
            if (articles.isNotEmpty()) {
                // set text for the existing textview
                this.text = articles.first()
                // click event
                openUrl(articles.first())

                for (articleUrl in 1 until articles.size) {
                    // create additional text views and them to the linear layout
                    layout.addView(createTextView(articles[articleUrl]))
                }
            }
        }

        @JvmStatic
        @BindingAdapter("app:openUrl")
        fun View.openUrl(url: String) {
            this.setOnClickListener {
                startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(url)), Bundle.EMPTY)
            }
        }

        @JvmStatic
        @BindingAdapter("visibility")
        fun View.visibility(count: Int) {
            this.isVisible = count > 0
        }

        // create text view
        private fun View.createTextView(url: String): TextView {
            return TextView(context).apply {
                setPadding(0, 24, 0, 0)
                setTextColor(ContextCompat.getColor(context, R.color.light_blue))
                text = url
                openUrl(url)
            }
        }
    }
}