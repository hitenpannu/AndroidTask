package com.hitenderpannu.auth.domain.authform

import com.hitenderpannu.auth.domain.AuthProcessInteractor
import com.hitenderpannu.auth.domain.AuthRequest
import com.hitenderpannu.auth.domain.AuthState
import com.tinder.StateMachine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class AuthFormInteractorImpl(private val authProcessInteractor: AuthProcessInteractor) : AuthFormInteractor {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            authProcessInteractor.observerState()
                .filter { it is AuthState.Failed }
                .collect { handleAuthState(it) }
        }
    }

    private val channel = ConflatedBroadcastChannel<AuthFormState>().apply {
        offer(AuthFormState.LoginState)
    }

    private val stateMachine = StateMachine.create<AuthFormState, AuthFormAction, AuthFormSideEffects> {
        initialState(AuthFormState.LoginState)
        state<AuthFormState.LoginState> {
            on<AuthFormAction.ToggleForm> { transitionTo(AuthFormState.SignUpState) }
            on<AuthFormAction.StartAuth> { transitionTo(AuthFormState.LoginInProcess(it.email, it.password)) }
            on<AuthFormAction.GuestLogin> { transitionTo(AuthFormState.GuestLoginInProcess) }
            on<AuthFormAction.GotAuthError> { dontTransition() }
            on<AuthFormAction.GotFormValidationError> { dontTransition() }
        }
        state<AuthFormState.SignUpState> {
            on<AuthFormAction.ToggleForm> { transitionTo(AuthFormState.LoginState) }
            on<AuthFormAction.StartAuth> { transitionTo(AuthFormState.SignUpInProcess(it.userName, it.email, it.password)) }
            on<AuthFormAction.GuestLogin> { transitionTo(AuthFormState.GuestLoginInProcess) }
            on<AuthFormAction.GotAuthError> { dontTransition() }
            on<AuthFormAction.GotFormValidationError> { dontTransition() }
        }
        state<AuthFormState.LoginInProcess> {
            on<AuthFormAction.ToggleForm> { dontTransition() }
            on<AuthFormAction.StartAuth> { dontTransition() }
            on<AuthFormAction.GuestLogin> { dontTransition() }
            on<AuthFormAction.GotAuthError> { transitionTo(AuthFormState.ErrorState.LoginError(it.message)) }
            on<AuthFormAction.GotFormValidationError> { transitionTo(AuthFormState.ErrorState.LoginError(it.message)) }
        }
        state<AuthFormState.SignUpInProcess> {
            on<AuthFormAction.ToggleForm> { dontTransition() }
            on<AuthFormAction.StartAuth> { dontTransition() }
            on<AuthFormAction.GuestLogin> { dontTransition() }
            on<AuthFormAction.GotAuthError> { transitionTo(AuthFormState.ErrorState.SignUpError(it.message)) }
            on<AuthFormAction.GotFormValidationError> { transitionTo(AuthFormState.ErrorState.SignUpError(it.message)) }
        }
        state<AuthFormState.ErrorState.LoginError> {
            on<AuthFormAction.ToggleForm> { transitionTo(AuthFormState.SignUpState) }
            on<AuthFormAction.StartAuth> {
                transitionTo(AuthFormState.LoginInProcess(it.email, it.password))
            }
            on<AuthFormAction.GuestLogin> { transitionTo(AuthFormState.GuestLoginInProcess) }
            on<AuthFormAction.GotAuthError> { transitionTo(AuthFormState.ErrorState.LoginError(it.message)) }
            on<AuthFormAction.GotFormValidationError> { transitionTo(AuthFormState.ErrorState.LoginError(it.message)) }
        }
        state<AuthFormState.ErrorState.SignUpError> {
            on<AuthFormAction.ToggleForm> { transitionTo(AuthFormState.SignUpState) }
            on<AuthFormAction.StartAuth> {
                transitionTo(AuthFormState.SignUpInProcess(it.userName, it.email, it.password))
            }
            on<AuthFormAction.GuestLogin> { transitionTo(AuthFormState.GuestLoginInProcess) }
            on<AuthFormAction.GotAuthError> { transitionTo(AuthFormState.ErrorState.SignUpError(it.message)) }
            on<AuthFormAction.GotFormValidationError> { transitionTo(AuthFormState.ErrorState.SignUpError(it.message)) }
        }
        state<AuthFormState.GuestLoginInProcess> {
            on<AuthFormAction.ToggleForm> { dontTransition() }
            on<AuthFormAction.StartAuth> { dontTransition() }
            on<AuthFormAction.GuestLogin> { dontTransition() }
            on<AuthFormAction.GotAuthError> { dontTransition() }
            on<AuthFormAction.GotFormValidationError> { dontTransition() }
        }

        onTransition {
            val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
            channel.offer(validTransition.toState)
            System.out.println(" ===== " + validTransition.toState)

            when (val newState = validTransition.toState) {
                AuthFormState.LoginState -> {
                }
                AuthFormState.SignUpState -> {
                }
                AuthFormState.GuestLoginInProcess -> {
                    authProcessInteractor.processRequest(AuthRequest.Guest)
                }
                is AuthFormState.LoginInProcess -> {
                    handleLogin(newState.email, newState.password)
                }
                is AuthFormState.SignUpInProcess -> {
                    handleSignUp(newState.userName, newState.email, newState.password)
                }
                is AuthFormState.ErrorState.LoginError -> {
                }
                is AuthFormState.ErrorState.SignUpError -> {
                }
            }
        }
    }

    override fun observeState(): Flow<AuthFormState> {
        return channel.asFlow()
    }

    override fun toggleForm() {
        stateMachine.transition(AuthFormAction.ToggleForm)
    }

    override fun performGuestLogin() {
        stateMachine.transition(AuthFormAction.GuestLogin)
    }

    override fun performAuthentication(username: String, email: String, password: String) {
        stateMachine.transition(AuthFormAction.StartAuth(username, email, password))
    }

    private fun handleLogin(email: String, password: String) {
        if (email.isNullOrBlank()) {
            stateMachine.transition(AuthFormAction.GotFormValidationError("Please provide valid Email"))
            return
        }
        if (password.isNullOrBlank()) {
            stateMachine.transition(AuthFormAction.GotFormValidationError("Please provide valid password"))
            return
        }
        authProcessInteractor.processRequest(AuthRequest.Login(email, password))
    }

    private fun handleSignUp(userName: String, email: String, password: String) {
        if (userName.isNullOrBlank()) {
            stateMachine.transition(AuthFormAction.GotFormValidationError("Please provide valid UserName"))
            return
        }
        if (email.isNullOrBlank()) {
            stateMachine.transition(AuthFormAction.GotFormValidationError("Please provide valid Email"))
            return
        }
        if (password.isNullOrBlank()) {
            stateMachine.transition(AuthFormAction.GotFormValidationError("Please provide valid password"))
            return
        }
        authProcessInteractor.processRequest(AuthRequest.SignUp(userName, email, password))
    }

    private fun handleAuthState(authProcessState: AuthState) {
        when (authProcessState) {
            is AuthState.Failed -> stateMachine.transition(AuthFormAction.GotAuthError(authProcessState.error.message ?: "Something went wrong"))
        }
    }
}
