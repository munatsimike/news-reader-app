package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import nl.project.newsreader2022.adapters.NewsAdapter
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.miscellaneous.ClickListener
import nl.project.newsreader2022.miscellaneous.showApiErrorFailure
import nl.project.newsreader2022.viewModel.NewsViewModel

// this class contains code that can be shared by all fragments
abstract class BaseFragment<VB : ViewBinding>(private val layoutInflater: (bindingInflater: LayoutInflater) -> VB) :
    Fragment(), ClickListener {
    val viewModel: NewsViewModel by activityViewModels()
    lateinit var newsAdapter: NewsAdapter

    private var _binding: VB? = null
    open val binding: VB
        get() {
            return _binding as VB
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsAdapter = NewsAdapter(this, viewModel)
        _binding = layoutInflater.invoke(inflater)
        showToast()

        if (_binding == null) {
            throw IllegalArgumentException("Binding cannot be null")
        }
        return binding.root
    }

    override fun onClickItem(view: View, article: NewsArticle) {
        viewModel.likeDislike(article)
    }

    //  display responses from api
    private fun showToast() {
        viewModel.toastData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it1 -> showApiErrorFailure(it1) }
        }
    }
}
