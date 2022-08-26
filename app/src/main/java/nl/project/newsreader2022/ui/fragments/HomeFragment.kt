package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.HomeFragmentBinding
import nl.project.newsreader2022.veiwModel.NewsViewModel
@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {
    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.articles.observe(viewLifecycleOwner){
            binding.hometext.text = it[0].PublishDate
        }

        viewModel.error.observe(viewLifecycleOwner){
            binding.hometext.text = it.toString()
        }
    }
}