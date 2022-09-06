package nl.project.newsreader2022.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.R
import nl.project.newsreader2022.databinding.HomeFragmentBinding
import nl.project.newsreader2022.databinding.ItemLoadingBinding
import nl.project.newsreader2022.model.numOfArticles
import nl.project.newsreader2022.utils.ClickListener

@AndroidEntryPoint
open class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate),
    ClickListener {
    private var nextId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsAdapter = newsAdapter
        displayArticles()
        initScrollListener()
        nextId()
    }

    private fun displayArticles() {
        viewModel.articles.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it.toMutableList())
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
        // newsAdapter.submitList(mutableListOf())
        if (nextId != 0)
            viewModel.getMoreArticles(nextId, numOfArticles())
    }
}
