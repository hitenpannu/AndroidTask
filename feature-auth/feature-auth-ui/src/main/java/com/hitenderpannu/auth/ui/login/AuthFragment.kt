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
import com.google.android.material.snackbar.Snackbar
import com.hitenderpannu.auth.ui.AuthViewModel
import com.hitenderpannu.auth.ui.ViewModelFactory
import com.hitenderpannu.auth.ui.databinding.FragmentLoginBinding
import com.hitenderpannu.auth.ui.di.DaggerManager
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AuthFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val authViewModel by lazy { ViewModelProviders.of(activity!!, viewModelFactory)[AuthViewModel::class.java] }
    private val viewModel: AuthFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthFragmentViewModel::class.java)
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
        binding?.authButton?.setOnClickListener {
            viewModel.startAuthProcess()
        }
        binding?.counterAuthButton?.setOnClickListener {
            viewModel.toggleAuthMode()
        }
    }

    private fun startObservingLiveData() {
        viewModel.userNameError.observe(viewLifecycleOwner, userNameErrorObserver)
        viewModel.userEmailError.observe(viewLifecycleOwner, userEmailErrorObserver)
        viewModel.passwordError.observe(viewLifecycleOwner, userPasswordErrorObserver)

        viewModel.authButtonEnabled.observe(viewLifecycleOwner, loginButtonStatusObserver)

        viewModel.authProgress.observe(viewLifecycleOwner, loginProgressObserver)
        viewModel.authSuccess.observe(viewLifecycleOwner, loginSuccessObserver)
        viewModel.authError.observe(viewLifecycleOwner, loginErrorObserver)

        viewModel.nameLayoutVisibilityLiveData.observe(viewLifecycleOwner, nameLayoutVisibilityObserver)
        viewModel.authLabelResource.observe(viewLifecycleOwner, authLabelResourceObserver)
        viewModel.authButtonActionResource.observe(viewLifecycleOwner, authButtonActionObserver)
        viewModel.counterButtonActionResource.observe(viewLifecycleOwner, counterAuthButtonActionObserver)

        viewModel.focusedField.observe(viewLifecycleOwner, focusedFieldObserver)
    }

    private val focusedFieldObserver = Observer<AuthFormField> {
        when(it) {
            AuthFormField.USER_NAME -> binding?.nameInputEditText?.requestFocus()
            AuthFormField.EMAIL -> binding?.emailInputEditText?.requestFocus()
            AuthFormField.PASSWORD -> binding?.passwordInputEditText?.requestFocus()
        }
    }

    private val counterAuthButtonActionObserver = Observer<Int> { resource ->
        binding?.counterAuthButton?.setText(resource)
    }

    private val authButtonActionObserver = Observer<Int> { resource->
        binding?.authButton?.setText(resource)
    }

    private val authLabelResourceObserver = Observer<Int> { resource->
        binding?.labelAuthTitle?.setText(resource)
    }

    private val nameLayoutVisibilityObserver = Observer<Boolean> { visibility ->
        binding?.nameInputLayout?.isVisible = visibility }

    private fun startObservingTextChanges() {
        binding?.nameInputEditText?.doAfterTextChanged { viewModel.updateUserName(it.toString()) }
        binding?.emailInputEditText?.doAfterTextChanged { viewModel.updateUserEmail(it.toString()) }
        binding?.passwordInputEditText?.doAfterTextChanged { viewModel.updatePassword(it.toString()) }
    }

    private val loginButtonStatusObserver = Observer<Boolean> {
        binding?.authButton?.isEnabled = it
    }

    private val userEmailErrorObserver = Observer<String?> {
        binding?.emailInputLayout?.error = it
    }

    private val userNameErrorObserver = Observer<String?> {
        binding?.nameInputLayout?.error = it
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
        if (show) binding?.progress?.show() else binding?.progress?.hide()
        binding?.authButton?.visibility = if(show) View.VISIBLE else View.INVISIBLE
        binding?.counterAuthButton?.isEnabled = !show
        binding?.emailInputLayout?.isEnabled = !show
        binding?.passwordInputLayout?.isEnabled = !show
        binding?.nameInputLayout?.isEnabled = !show
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
