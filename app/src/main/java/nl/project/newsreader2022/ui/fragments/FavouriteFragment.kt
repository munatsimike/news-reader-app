package nl.project.newsreader2022.ui.fragments

import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.FavouriteFragmentBinding
import nl.project.newsreader2022.model.LikedArticle

@AndroidEntryPoint
class FavouriteFragment: BaseFragment<FavouriteFragmentBinding>(FavouriteFragmentBinding::inflate) {
    override fun onClickItem(article: LikedArticle) {
        TODO("Not yet implemented")
    }
}