package com.hitenderpannu.auth.domain.login

import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.auth.data.network.entity.NetworkUser
import com.hitenderpannu.auth.data.network.entity.SignupResponse
import com.hitenderpannu.auth.entity.User
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.common.entity.NetworkResponse
import com.hitenderpannu.common.entity.Status
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginInteractorImplTest {

    @Mock
    lateinit var networkConnectionChecker: NetworkConnectionChecker

    @Mock
    lateinit var authRepo: AuthRepo

    @Mock
    lateinit var userPreferences: UserPreferences

    @InjectMocks
    lateinit var loginInteractorImpl: LoginInteractorImpl

    @Test
    fun `return internet error`() {
        runBlocking {
            Mockito.`when`(networkConnectionChecker.isConnected()).thenReturn(false)
            try {
                loginInteractorImpl.login("email", "12345678")
            } catch (error: Throwable) {
                Assert.assertTrue(error is NoInternetConnection)
            }
            Mockito.verify(networkConnectionChecker).isConnected()
        }
    }

    @Test
    fun `return user from auth repo`() {
        runBlocking {
            Mockito.`when`(networkConnectionChecker.isConnected()).thenReturn(true)

            val mockedUser = User("id", "name", "email","token")
            Mockito.`when`(authRepo.login("email", "12345678")).thenReturn(mockedUser)

            val response = loginInteractorImpl.login("email", "12345678")

            Mockito.verify(authRepo).login("email", "12345678")

            Mockito.verify(userPreferences).userId =  "id"
            Mockito.verify(userPreferences).userName = "name"
            Mockito.verify(userPreferences).userEmail = "email"
            Mockito.verify(userPreferences).userToken = "token"

            Assert.assertEquals(response, mockedUser)
        }
    }
}
