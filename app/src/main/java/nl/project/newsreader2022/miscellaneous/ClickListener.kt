package nl.project.newsreader2022.miscellaneous

import nl.project.newsreader2022.model.NewsArticle


interface ClickListener {
    fun onClickItem(article: NewsArticle)
}