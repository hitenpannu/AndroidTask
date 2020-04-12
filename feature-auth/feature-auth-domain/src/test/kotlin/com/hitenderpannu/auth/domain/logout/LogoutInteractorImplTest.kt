package com.hitenderpannu.auth.domain.logout

import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.common.domain.UserPreferences
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
class LogoutInteractorImplTest {

    @Mock
    lateinit var authRepo: AuthRepo

    @Mock
    lateinit var userPreferences: UserPreferences

    @Mock
    lateinit var networkConnectionChecker: NetworkConnectionChecker

    @InjectMocks
    lateinit var logoutInteractorImpl: LogoutInteractorImpl

    @Test
    fun `should throw no internet connection`() {
        runBlocking {
            Mockito.`when`(networkConnectionChecker.isConnected()).thenReturn(false)
            try {
                logoutInteractorImpl.logout()
            } catch (error: Throwable) {
                Assert.assertTrue(error is NoInternetConnection)
            }
            Mockito.verify(networkConnectionChecker).isConnected()
        }
    }

    @Test
    fun `should logout without any error`() {
        runBlocking {
            Mockito.`when`(networkConnectionChecker.isConnected()).thenReturn(true)
            Mockito.`when`(authRepo.logout()).thenReturn(Unit)

            logoutInteractorImpl.logout()

            Mockito.verify(authRepo).logout()
            Mockito.verify(userPreferences).userId = null
            Mockito.verify(userPreferences).userName = null
            Mockito.verify(userPreferences).userEmail = null
            Mockito.verify(userPreferences).userToken = null
        }
    }
}
