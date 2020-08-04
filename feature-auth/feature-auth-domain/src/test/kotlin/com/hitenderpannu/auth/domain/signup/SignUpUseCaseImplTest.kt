package com.hitenderpannu.auth.domain.signup

import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.auth.entity.User
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignUpUseCaseImplTest {

    @Mock
    lateinit var authRepo: AuthRepo

    @Mock
    lateinit var userPreferences: UserPreferences

    @Mock
    lateinit var networkConnectionChecker: NetworkConnectionChecker

    @InjectMocks
    lateinit var signUpInteractorImpl: SignUpUseCaseImpl

    @Test
    fun `throw no internet connection`() {
        runBlocking {
            Mockito.`when`(networkConnectionChecker.isConnected()).thenReturn(false)
            try {
                signUpInteractorImpl.signUp("name", "email", "password")
            } catch (error: Throwable) {
                assertEquals(error, NoInternetConnection)
            }
        }
    }

    @Test
    fun `signUp success`() {
        runBlocking {
            Mockito.`when`(networkConnectionChecker.isConnected()).thenReturn(true)
            val mockedUser = User("id", "name", "email", "token")
            Mockito.`when`(authRepo.signup("name", "email", "password")).thenReturn(mockedUser)
            val response = signUpInteractorImpl.signUp("name", "email", "password")

            assertEquals(response, mockedUser)

            Mockito.verify(userPreferences).userId = "id"
            Mockito.verify(userPreferences).userName = "name"
            Mockito.verify(userPreferences).userEmail = "email"
            Mockito.verify(userPreferences).userToken = "token"
        }
    }
}
