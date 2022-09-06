package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nl.project.newsreader2022.databinding.FavouriteFragmentBinding

@AndroidEntryPoint
class FavouriteFragment: BaseFragment<FavouriteFragmentBinding>(FavouriteFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsAdapter = newsAdapter
        refreshLikedArticles()
        displayFavourites()
    }

    private fun displayFavourites(){
        viewModel.likedArticles.observe(viewLifecycleOwner){
            newsAdapter.submitList(it.toMutableList())
        }
    }

    private fun refreshLikedArticles(){
        lifecycleScope.launch {
            viewModel.refreshLikedArticles()
        }
    }
}