package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.LoginBottomSheetBinding
import nl.project.newsreader2022.viewModel.UserViewModel

@AndroidEntryPoint
class LoginBottomSheet : BottomSheetDialogFragment() {
    private val sharedViewModel: UserViewModel by activityViewModels()
    private lateinit var binding: LoginBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginBottomSheetBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = sharedViewModel
        return binding.root
    }
}