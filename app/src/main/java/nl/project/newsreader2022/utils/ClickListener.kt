package nl.project.newsreader2022.utils

import android.view.View
import nl.project.newsreader2022.model.NewsArticle


interface ClickListener {
    fun onClickItem(view: View, article: NewsArticle)
}