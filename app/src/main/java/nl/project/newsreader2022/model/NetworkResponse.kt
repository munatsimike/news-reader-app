package nl.project.newsreader2022.model

data class NetworkResponse(
    val NextId: Int,
    val Results: List<NewsArticle>
)