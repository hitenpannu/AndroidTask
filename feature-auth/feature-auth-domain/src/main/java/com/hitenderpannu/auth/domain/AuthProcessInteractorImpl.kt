package com.hitenderpannu.auth.domain

import com.hitenderpannu.auth.domain.guest.GuestLoginUseCase
import com.hitenderpannu.auth.domain.login.LoginUseCase
import com.hitenderpannu.auth.domain.signup.SignUpUseCase
import com.tinder.StateMachine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class AuthProcessInteractorImpl(
    private val guestLoginUseCase: GuestLoginUseCase,
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase
) : AuthProcessInteractor {

    private val channel = ConflatedBroadcastChannel<AuthState>().apply {
        offer(AuthState.Init)
    }

    private val stateMachine = StateMachine.create<AuthState, AuthAction, AuthSideEffects> {
        initialState(AuthState.Init)
        state<AuthState.Init> {
            on<AuthAction.Request> {
                transitionTo(AuthState.Loading(it.authRequest))
            }
            on<AuthAction.AuthSuccess> {
                dontTransition()
            }
            on<AuthAction.AuthFailed> {
                dontTransition()
            }
        }
        state<AuthState.Loading> {
            on<AuthAction.Request> {
                dontTransition()
            }
            on<AuthAction.AuthSuccess> {
                transitionTo(AuthState.Authenticated)
            }
            on<AuthAction.AuthFailed> {
                transitionTo(AuthState.Failed(it.error))
            }
        }
        state<AuthState.Failed> {
            on<AuthAction.Request> {
                transitionTo(AuthState.Loading(it.authRequest))
            }
            on<AuthAction.AuthFailed> {
                dontTransition()
            }
            on<AuthAction.AuthSuccess> {
                dontTransition()
            }
        }
        state<AuthState.Authenticated> {
            on<AuthAction.Request> {
                dontTransition()
            }
            on<AuthAction.AuthFailed> {
                dontTransition()
            }
            on<AuthAction.AuthSuccess> {
                dontTransition()
            }
        }
        onTransition {
            val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition

            channel.offer(validTransition.toState)

            when (val newState = validTransition.toState) {
                is AuthState.Loading -> handleAuthRequest(newState.authRequest)
                is AuthState.Authenticated -> channel.offer(newState)
                is AuthState.Failed -> channel.offer(newState)
            }

            System.out.println(" ===== "+ validTransition.toState)
        }
    }

    override fun observerState(): Flow<AuthState> {
        return channel.asFlow()
    }

    override fun processRequest(authRequest: AuthRequest) {
        stateMachine.transition(AuthAction.Request(authRequest))
    }

    private fun handleAuthRequest(authRequest: AuthRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (authRequest) {
                    AuthRequest.Guest -> guestLoginUseCase.login()
                    is AuthRequest.Login -> loginUseCase.login(authRequest.email, authRequest.password)
                    is AuthRequest.SignUp -> signUpUseCase.signUp(authRequest.userName, authRequest.email, authRequest.password)
                }
                stateMachine.transition(AuthAction.AuthSuccess)
            } catch (exception: Throwable) {
                stateMachine.transition(AuthAction.AuthFailed(exception))
            }
        }
    }
}
