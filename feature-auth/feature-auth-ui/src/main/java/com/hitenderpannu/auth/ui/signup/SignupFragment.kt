package com.hitenderpannu.auth.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hitenderpannu.auth.entity.User
import com.hitenderpannu.auth.ui.AuthViewModel
import com.hitenderpannu.auth.ui.R
import com.hitenderpannu.auth.ui.ViewModelFactory
import com.hitenderpannu.auth.ui.databinding.FragmentSignupBinding
import com.hitenderpannu.auth.ui.di.DaggerManager
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SignupFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val authViewModel by lazy { ViewModelProviders.of(activity!!, viewModelFactory)[AuthViewModel::class.java] }
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory)[SignupFragmentViewModel::class.java] }
    private var binding: FragmentSignupBinding? = null

    private var performDependencyInjection = { DaggerManager.inject(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        performDependencyInjection.invoke()

        binding = FragmentSignupBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObservingFieldsForTextChanges()
        attachErrorLiveDataWithFields()

        binding?.buttonSignup?.setOnClickListener { viewModel.startSignUp() }
        binding?.buttonMoveToLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun attachErrorLiveDataWithFields() {
        viewModel.mutableUserNameError.observe(this, userNameErrorObserver)
        viewModel.mutableEmailError.observe(this, emailErrorObserver)
        viewModel.mutablePasswordError.observe(this, passwordErrorObserver)
        viewModel.mutableConfirmPasswordError.observe(this, confirmPasswordErrorObserver)
        viewModel.shouldEnableSignUpButton.observe(this, signUpButtonStatusObserver)
        viewModel.progress().observe(this, progressObserver)
        viewModel.signUpError().observe(this, errorObserver)
    }

    private fun startObservingFieldsForTextChanges() {
        binding?.nameInputEditText?.doAfterTextChanged { viewModel.updateUserName(it.toString()) }
        binding?.emailInputEditText?.doAfterTextChanged { viewModel.updateEmail(it.toString()) }
        binding?.passwordInputEditText?.doAfterTextChanged { viewModel.updateUserPassword(it.toString()) }
        binding?.confirmPasswordInputEditText?.doAfterTextChanged { viewModel.updateUserConfirmPassword(it.toString()) }
    }

    private val userNameErrorObserver = Observer<String?> { binding?.nameInputLayout?.error = it }
    private val emailErrorObserver = Observer<String?> { binding?.emailInputLayout?.error = it }
    private val passwordErrorObserver = Observer<String?> { binding?.passwordInputLayout?.error = it }
    private val confirmPasswordErrorObserver = Observer<String?> { binding?.confirmPasswordInputLayout?.error = it }
    private val signUpButtonStatusObserver = Observer<Boolean> { binding?.buttonSignup?.isEnabled = it }
    private val progressObserver = Observer<Boolean> { isInProgress ->
        binding?.nameInputEditText?.isEnabled = !isInProgress
        binding?.emailInputEditText?.isEnabled = !isInProgress
        binding?.passwordInputEditText?.isEnabled = !isInProgress
        binding?.confirmPasswordInputEditText?.isEnabled = !isInProgress
        binding?.buttonSignup?.isVisible = !isInProgress
        if (isInProgress) binding?.signUpProgress?.show() else binding?.signUpProgress?.hide()
    }

    private val userObserver = Observer<User> { authViewModel.isAuthenticationDone.postValue(true) }
    private val errorObserver = Observer<String> {
        Snackbar.make(binding!!.root, it, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onDestroyView() {
        deattachErrorLiveDataObserver()
        super.onDestroyView()
    }

    private fun deattachErrorLiveDataObserver() {
        viewModel.mutableUserNameError.removeObserver(userNameErrorObserver)
        viewModel.mutableEmailError.removeObserver(emailErrorObserver)
        viewModel.mutablePasswordError.removeObserver(passwordErrorObserver)
        viewModel.mutableConfirmPasswordError.removeObserver(confirmPasswordErrorObserver)
    }
}
