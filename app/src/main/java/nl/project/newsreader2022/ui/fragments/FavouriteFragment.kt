package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.FavouriteFragmentBinding

@AndroidEntryPoint
// this class contains code to display liked articles
class FavouriteFragment :
    BaseFragment<FavouriteFragmentBinding>(FavouriteFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsAdapter = newsAdapter
        refreshLikedArticles()
        displayFavourites()
    }

    private fun displayFavourites() {
        viewModel.likedArticles.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it.toMutableList())
        }
    }

    private fun refreshLikedArticles() {
        viewModel.refreshLikedArticles()
    }
}