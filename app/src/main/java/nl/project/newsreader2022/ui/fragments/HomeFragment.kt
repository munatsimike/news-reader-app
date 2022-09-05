package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.adapters.NewsAdapter
import nl.project.newsreader2022.databinding.HomeFragmentBinding
import nl.project.newsreader2022.model.LikedArticle
import nl.project.newsreader2022.model.numOfArticles
import nl.project.newsreader2022.ui.ClickListener
import nl.project.newsreader2022.viewModel.NewsViewModel


@AndroidEntryPoint
open class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate),
    ClickListener {
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private var nextId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsAdapter = NewsAdapter(this)
        displayArticles()
        setupRecyclerView()
        initScrollListener()
        nextId()
    }

    override fun onClickItem(article: LikedArticle) {
        TODO("Not yet implemented")
    }

    // initialize recycler view
    private fun setupRecyclerView() {
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun displayArticles() {
        viewModel.articles.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }
    }

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
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    //bottom of list!
                    loadMore()
                }
            }
        })
    }

    private fun loadMore() {
        if (nextId != 0)
            viewModel.getMoreArticles(nextId, numOfArticles())
    }
}
