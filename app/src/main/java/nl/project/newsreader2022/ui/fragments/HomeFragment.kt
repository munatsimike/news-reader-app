package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.adapters.NewsAdapter
import nl.project.newsreader2022.databinding.HomeFragmentBinding
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.model.numOfArticles
import nl.project.newsreader2022.viewModel.NewsViewModel

@AndroidEntryPoint
open class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate){
    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var newsAdapter: NewsAdapter
    private var nextId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsAdapter = NewsAdapter(this, viewModel)
        binding.newsAdapter = newsAdapter
        displayArticles()
        initScrollListener()
        nextId()
    }

    private fun displayArticles() {
        viewModel.articles.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }
    }

    // next id needed for endless scrolling
    private fun nextId() {
        viewModel.nextId.observe(viewLifecycleOwner) {
            if (it != null)
                nextId = it
        }
    }

    open fun initScrollListener() {
        binding.rvBreakingNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // check if recycler cannot scroll vertically
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    loadMore()
                }
            }
        })
    }

    // get more articles
    private fun loadMore() {
        if (nextId != 0)
            viewModel.getMoreArticles(nextId, numOfArticles())
    }

    override fun onClickItem(view: View, article: NewsArticle) {
        viewModel.likeDislike(article)
    }
}
