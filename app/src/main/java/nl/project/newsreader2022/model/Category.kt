package nl.project.newsreader2022.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val Id: Int,
    val Name: String
)