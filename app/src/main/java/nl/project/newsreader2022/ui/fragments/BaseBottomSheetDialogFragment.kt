package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import nl.project.newsreader2022.viewModel.UserViewModel

// this class contains code for the login and logout bottom sheet dialogue fragment
open class BaseBottomSheetDialogFragment<VB : ViewBinding>(private val layoutInflater: (bindingInflater: LayoutInflater) -> VB) :
    BottomSheetDialogFragment() {
    val sharedViewModel: UserViewModel by activityViewModels()

    // to store user registration and login error or success state
    protected val _isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess: LiveData<Boolean> = _isSuccess

    // success or error message to be displayed for the user
    protected val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

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