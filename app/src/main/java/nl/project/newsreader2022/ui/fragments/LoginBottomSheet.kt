package nl.project.newsreader2022.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val registrationResponse: MutableLiveData<String> = MutableLiveData()
    val response: LiveData<String> = registrationResponse

    private val _isRegistered: MutableLiveData<Boolean> = MutableLiveData()
    val isRegistered: LiveData<Boolean> = _isRegistered

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRegistrationResponse()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = sharedViewModel
        binding.loginBottomSheet = this
    }

    private fun showRegistrationResponse() {
        sharedViewModel.toastData.observe(viewLifecycleOwner) { response ->
            response.getContentIfNotHandled().let {
                it?.onSuccess {
                    if (this.data is UserRegistrationResponse) {
                        val result = (this.data as UserRegistrationResponse)
                        _isRegistered.value = result.Success
                        registrationResponse.value = result.Message
                        sharedViewModel.setLoginRegisterBtnText(!result.Success)
                    }
                }?.onError { showApiErrorFailure(this) }
                    ?.onFailure { showApiErrorFailure(this) }
            }
        }
    }
}


