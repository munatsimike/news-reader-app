package nl.project.newsreader2022.model

import java.io.Serializable

@kotlinx.serialization.Serializable
data class Category(
    val Id: Int,
    val Name: String
): Serializable