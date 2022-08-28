package nl.project.newsreader2022.model

import nl.project.newsreader2022.model.Article

data class LikedArticle(val id: Int, val url: String) : Article(id,url)