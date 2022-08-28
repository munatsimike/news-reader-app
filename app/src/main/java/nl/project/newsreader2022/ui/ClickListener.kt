package nl.project.newsreader2022.ui

import nl.project.newsreader2022.model.LikedArticle

interface ClickListener {
    fun onClickItem(article: LikedArticle)
}