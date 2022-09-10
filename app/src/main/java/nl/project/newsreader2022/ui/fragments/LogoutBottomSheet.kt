package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.LogoutBottomSheetBinding

@AndroidEntryPoint
// this class contains code for the lout bottom sheet
class LogoutBottomSheet :
    BaseBottomSheetDialogFragment<LogoutBottomSheetBinding>(LogoutBottomSheetBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = sharedViewModel
    }
}