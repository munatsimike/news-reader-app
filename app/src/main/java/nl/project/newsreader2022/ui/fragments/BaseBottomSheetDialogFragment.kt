package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import nl.project.newsreader2022.viewModel.UserViewModel

// this class contains code for the login and logout bottom sheet dialogue fragment
open class BaseBottomSheetDialogFragment<VB : ViewBinding>(private val layoutInflater: (bindingInflater: LayoutInflater) -> VB) :
    BottomSheetDialogFragment() {
    val sharedViewModel: UserViewModel by activityViewModels()

    private var _binding: VB? = null
    open val binding: VB
        get() {
            return _binding as VB
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = layoutInflater.invoke(inflater)
        if (_binding == null) {
            throw IllegalArgumentException("Binding cannot be null")
        }
        return binding.root
    }
}