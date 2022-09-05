package nl.project.newsreader2022.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import nl.project.newsreader2022.databinding.ItemArticlePreviewBinding
import nl.project.newsreader2022.databinding.ItemLoadingBinding
import nl.project.newsreader2022.model.LikedArticle
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.ui.ClickListener

// recycler view  view with view binding and diffUtil
class NewsAdapter(
    private var clickListener: ClickListener,
) : ListAdapter<NewsArticle, RecyclerView.ViewHolder>(NewsDiffCallBack()) {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsArticles: NewsArticle) {
            with(newsArticles) {
                with(binding) {
                    ivArticleImage.load(Image)
                    tvTitle.text = Title
                    tvDescription.text = Summary
                    tvPublishedAt.text = PublishDate
                    tvSource.text = Id.toString()
                }
            }

            // set onclick listener
            binding.tvTitle.setOnClickListener {
                clickListener.onClickItem(
                    LikedArticle(
                        newsArticles.Id,
                        newsArticles.Url
                    )
                )
            }
        }
    }

    inner class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun showProgressBar() {
            binding.progressBar
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val itemBinding = ItemArticlePreviewBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ArticleViewHolder(itemBinding)
        }
        val itemLoadingBinding =
            ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingViewHolder(itemLoadingBinding);
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList.lastIndex  == position) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            holder.bind(getItem(position))
        } else {
            (holder as LoadingViewHolder).showProgressBar()
        }
    }
}