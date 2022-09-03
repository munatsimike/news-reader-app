package nl.project.newsreader2022.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import nl.project.newsreader2022.databinding.ItemArticlePreviewBinding
import nl.project.newsreader2022.model.LikedArticle
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.ui.ClickListener
import javax.inject.Inject

// recycler view  view with view binding and diffUtil

class NewsAdapter (
    private var clickListener: ClickListener,
) : ListAdapter<NewsArticle, NewsAdapter.ArticleViewHolder>(NewsDiffCallBack()) {

    private lateinit var binding: ItemArticlePreviewBinding

    @Inject
    lateinit var paginationCallBack: PaginationCallBack

    inner class ArticleViewHolder(binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: LikedArticle, clickListener: ClickListener?) {
            binding.tvTitle.setOnClickListener { clickListener?.onClickItem(article) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        binding = ItemArticlePreviewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val newsArticles = getItem(position)
        with(newsArticles) {
            binding.ivArticleImage.load(Image)
            binding.tvTitle.text = Title
            binding.tvDescription.text = Summary
            binding.tvPublishedAt.text = PublishDate
        }
        holder.bind(LikedArticle(newsArticles.Id, newsArticles.Url), clickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
