package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import nl.project.newsreader2022.adapters.NewsAdapter
import nl.project.newsreader2022.miscellaneous.listeners.ClickListener
import nl.project.newsreader2022.miscellaneous.showApiErrorFailure
import nl.project.newsreader2022.model.NewsArticle
import nl.project.newsreader2022.network.updateHeaderToken
import nl.project.newsreader2022.viewModel.NewsViewModel
import nl.project.newsreader2022.viewModel.UserViewModel

// this class contains code that can be shared by all fragments
abstract class BaseFragment<VB : ViewBinding>(private val layoutInflater: (bindingInflater: LayoutInflater) -> VB) :
    Fragment(), ClickListener {
    val viewModel: NewsViewModel by activityViewModels()
    val userViewModel: UserViewModel by activityViewModels()

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

        authTokenObserver()
        return binding.root
    }

    // navigate to the details page from any destination
    override fun onClickItem(article: NewsArticle) {
        findNavController().navigate(HomeFragmentDirections.actionGlobalDetailsFragment(article))
    }

    //  display responses from api
    private fun showToast() {
        viewModel.toastMessage.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it1 -> showApiErrorFailure(it1) {viewModel.refreshArticles()} }
        }
    }

    // observe token changes
    private fun authTokenObserver() {
        userViewModel.authToken.asLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                // update user service
                updateHeaderToken(it)
            }
        }
    }

    fun delay(duration: Long,  action: ()->Unit){
        Handler(Looper.getMainLooper()).postDelayed({
            action.invoke()
        }, duration)
    }
}
