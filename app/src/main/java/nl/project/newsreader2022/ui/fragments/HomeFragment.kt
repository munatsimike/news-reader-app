package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.HomeFragmentBinding

@AndroidEntryPoint
open class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    private var nextId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsAdapter = newsAdapter
        displayArticles()
        initScrollListener()
        nextNewsArticleId()
    }

    private fun displayArticles() {
        viewModel.articles.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it.toMutableList())
        }
    }

    // detect changes to next news article id needed for endless scrolling
    private fun nextNewsArticleId() {
        viewModel.nextId.observe(viewLifecycleOwner) {
            if (it != null)
                nextId = it
        }
    }

    // recycler view scroll listener to detect if recycler has scrolled to the last item
    open fun initScrollListener() {
        binding.rvBreakingNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // check if recycler cannot scroll vertically
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    recyclerView.post {
                        loadMoreArticles()
                    }
                }
            }
        })
    }

    // get more articles after the first batch has scrolled to the bottom of the recycler view
    private fun loadMoreArticles() {
        if (nextId != 0)
            viewModel.getMoreArticles(nextId, NUM_OF_ARTICLES)
    }

    companion object {
        const val NUM_OF_ARTICLES = 20
    }
}
