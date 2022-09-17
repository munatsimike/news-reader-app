package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.HomeFragmentBinding
import nl.project.newsreader2022.miscellaneous.listeners.PaginationScrollListener

@AndroidEntryPoint
open class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    private var nextId = 0
    private var isRefreshing: Boolean = false
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsAdapter = newsAdapter
        displayArticles()
        initScrollListener()
        initSwipeToRefresh()
        nextNewsArticleId()
    }

    private fun displayArticles() {
        viewModel.articles.observe(viewLifecycleOwner) {
            switchOffLoading()
            newsAdapter.submitList(it.toMutableList())
            delay(1000) { switchOffRefresher() }
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
        binding.rvBreakingNews.addOnScrollListener(object : PaginationScrollListener() {
            override fun loadMoreItems() {
                isLoading.value = true
                // add null mark at the end of the list. The marker will be used to change view type and show progress bar
                newsAdapter.addNullMarker()
                // delay loading the articles to allow the progressbar to be displayed
                delay(2500) { loadMoreArticles() }
            }

            override fun isLoading(): MutableLiveData<Boolean> {
                return isLoading
            }
        })
    }

    // get more articles after the previous batch has scrolled to the bottom of the recycler view
    private fun loadMoreArticles() {
        if (nextId != 0) {
            // fetch news articles
            viewModel.getMoreArticles(nextId, NUM_OF_ARTICLES)

            isLoading.observe(this) {
                // remove the null marker when items have been loaded to the recycler view
                newsAdapter.removeNullMarker()
            }
        }
    }

    open fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            isRefreshing = true
            delay(1500) { refreshArticles() }
        }
    }

    // fetch articles from api
    private fun refreshArticles() {
        newsAdapter.currentList.toMutableList().clear()
        viewModel.refreshArticles()
    }

    // switch off refreshing progress bar
    private fun switchOffRefresher() {
        if (isRefreshing) {
            isRefreshing = false
            binding.swipeRefresh.isRefreshing = false
        }
    }

    // turn off is loading when new items have been received
    private fun switchOffLoading() {
        if (isLoading.value == true) {
            isLoading.value = false
        }
    }

    // number of articles to fetch from remote server
    companion object {
        const val NUM_OF_ARTICLES = 20
    }
}
