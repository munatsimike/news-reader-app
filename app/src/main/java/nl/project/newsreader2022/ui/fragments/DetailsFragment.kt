package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import nl.project.newsreader2022.databinding.DetailsFragmentBinding
import nl.project.newsreader2022.miscellaneous.popBackStack

class DetailsFragment : BaseFragment<DetailsFragmentBinding>(DetailsFragmentBinding::inflate) {
    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.newsViewModel = viewModel
        binding.article = args.article
        backButtonClickListener()
    }

    private fun backButtonClickListener(){
        binding.backArrow.setOnClickListener {
            popBackStack()
        }
    }
}