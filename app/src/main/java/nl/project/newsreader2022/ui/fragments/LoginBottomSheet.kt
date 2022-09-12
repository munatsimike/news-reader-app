package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.LoginBottomSheetBinding
import nl.project.newsreader2022.miscellaneous.showApiErrorFailure
import nl.project.newsreader2022.model.UserRegistrationResponse

@AndroidEntryPoint
class LoginBottomSheet :
    BaseBottomSheetDialogFragment<LoginBottomSheetBinding>(LoginBottomSheetBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRegistrationResponse()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = sharedViewModel
        binding.loginBottomSheet = this
    }

    private fun showRegistrationResponse() {
        sharedViewModel.toastMessage.observe(viewLifecycleOwner) { response ->
            response.getContentIfNotHandled().let {
                it?.onSuccess {
                    if (this.data is UserRegistrationResponse) {
                        val result = (this.data as UserRegistrationResponse)
                        _isSuccess.value = result.Success
                        _message.value = result.Message
                        sharedViewModel.setLoginRegisterBtnText(!result.Success)
                    }
                }?.onError { showApiErrorFailure(this) }
                    ?.onFailure { showApiErrorFailure(this) }
            }
        }
    }
}


