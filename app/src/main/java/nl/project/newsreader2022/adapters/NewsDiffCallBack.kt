package nl.project.newsreader2022.adapters

import androidx.recyclerview.widget.DiffUtil
import nl.project.newsreader2022.model.NewsArticle

class NewsDiffCallBack: DiffUtil.ItemCallback<NewsArticle>() {
    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem == newItem
    }
}