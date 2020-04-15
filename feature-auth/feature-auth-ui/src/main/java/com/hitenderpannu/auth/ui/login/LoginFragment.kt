package com.hitenderpannu.auth.ui.login

import android.content.Context
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
import com.hitenderpannu.auth.ui.AuthViewModel
import com.hitenderpannu.auth.ui.R
import com.hitenderpannu.auth.ui.ViewModelFactory
import com.hitenderpannu.auth.ui.databinding.FragmentLoginBinding
import com.hitenderpannu.auth.ui.di.DaggerManager
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val authViewModel by lazy { ViewModelProviders.of(activity!!, viewModelFactory)[AuthViewModel::class.java] }
    private val viewModel: LoginFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginFragmentViewModel::class.java)
    }

    private var binding: FragmentLoginBinding? = null
    private var performDependencyInjection: (() -> Unit)? = { DaggerManager.inject(this) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        performDependencyInjection?.invoke()
        performDependencyInjection = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservingTextChanges()
        startObservingLiveData()
        binding?.buttonLogin?.setOnClickListener {
            viewModel.startLoginProcess()
        }
        binding?.buttonMoveToSignUp?.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun startObservingLiveData() {
        viewModel.userEmailError.observe(this, userNameErrorObserver)
        viewModel.userPasswordError.observe(this, userPasswordErrorObserver)

        viewModel.shouldEnableLoginButton.observe(this, loginButtonStatusObserver)

        viewModel.loginProgress.observe(this, loginProgressObserver)
        viewModel.loginSuccess.observe(this, loginSuccessObserver)
        viewModel.loginError.observe(this, loginErrorObserver)
    }

    private fun startObservingTextChanges() {
        binding?.emailInputEditText?.doAfterTextChanged { viewModel.updateUserEmail(it.toString()) }
        binding?.passwordInputEditText?.doAfterTextChanged { viewModel.updatePassword(it.toString()) }
    }

    private val loginButtonStatusObserver = Observer<Boolean> {
        binding?.buttonLogin?.isEnabled = it
    }

    private val userNameErrorObserver = Observer<String?> {
        binding?.emailInputLayout?.error = it
    }

    private val userPasswordErrorObserver = Observer<String?> {
        binding?.passwordInputLayout?.error = it
    }

    private val loginSuccessObserver = Observer<Boolean> {
        authViewModel.isAuthenticationDone.postValue(it)
    }

    private val loginErrorObserver = Observer<String> {
        Snackbar.make(binding!!.root, it, Snackbar.LENGTH_INDEFINITE).show()
    }

    private val loginProgressObserver = Observer<Boolean> { show ->
        if (show) binding?.loginProgress?.show() else binding?.loginProgress?.hide()
        binding?.buttonLogin?.isVisible = !show
        binding?.buttonMoveToSignUp?.isEnabled = !show
        binding?.emailInputLayout?.isEnabled = !show
        binding?.passwordInputLayout?.isEnabled = !show
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
