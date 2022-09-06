package nl.project.newsreader2022.ui.fragments

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.FavouriteFragmentBinding
import nl.project.newsreader2022.model.NewsArticle

@AndroidEntryPoint
class FavouriteFragment: BaseFragment<FavouriteFragmentBinding>(FavouriteFragmentBinding::inflate) {
    override fun onClickItem(view: View, article: NewsArticle) {
        TODO("Not yet implemented")
    }
}