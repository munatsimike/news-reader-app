package nl.project.newsreader2022.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.project.newsreader2022.databinding.ItemArticlePreviewBinding
import nl.project.newsreader2022.databinding.ItemLoadingBinding
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.miscellaneous.listeners.ClickListener
import nl.project.newsreader2022.viewModel.NewsViewModel

// recycler view  view with view binding and diffUtil
class NewsAdapter(
    private val clickListener: ClickListener,
    private val newsViewModel: NewsViewModel
) : ListAdapter<NewsArticle, RecyclerView.ViewHolder>(NewsDiffCallBack()) {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    inner class ArticleViewHolder(val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsArticles: NewsArticle) {
            binding.article = newsArticles
            binding.clickListener = clickListener
            binding.newsViewModel = newsViewModel
        }
    }

    inner class LoadingViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun showProgressBar() {
            binding.progressBar.visibility
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
        return if (currentList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            holder.bind(getItem(position))
        } else {
            (holder as LoadingViewHolder).showProgressBar()
        }
    }

    fun addNullMarker() {
        val currentList = currentList.toMutableList()
        currentList.add(null)
        submitList(currentList)
    }

    fun removeNullMarker() {
        val currentList = currentList.toMutableList()
        if (currentList.size > 0 && currentList.last() == null)
            currentList.removeLast()
        submitList(currentList)
    }
}