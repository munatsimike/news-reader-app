package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import nl.project.newsreader2022.adapters.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.HomeFragmentBinding
import nl.project.newsreader2022.model.LikedArticle
import nl.project.newsreader2022.ui.ClickListener
import nl.project.newsreader2022.viewModel.NewsViewModel

@AndroidEntryPoint
open class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate), ClickListener {
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsAdapter = NewsAdapter(this)
        setupRecyclerView()
        displayArticles()
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
}