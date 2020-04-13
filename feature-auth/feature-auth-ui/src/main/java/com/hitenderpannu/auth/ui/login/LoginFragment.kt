package com.hitenderpannu.auth.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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

    private var binding: FragmentLoginBinding? = null
    private var performDependencyInjection: (() -> Unit)? = { DaggerManager.inject(this) }

    private val viewModel: LoginFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginFragmentViewModel::class.java)
    }

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

        binding?.buttonMoveToSignUp?.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
