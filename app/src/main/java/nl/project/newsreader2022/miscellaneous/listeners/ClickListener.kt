package nl.project.newsreader2022.miscellaneous.listeners

import nl.project.newsreader2022.model.NewsArticle


interface ClickListener {
    fun onClickItem(article: NewsArticle)
}