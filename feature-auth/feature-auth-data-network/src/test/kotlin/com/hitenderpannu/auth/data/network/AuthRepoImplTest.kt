package com.hitenderpannu.auth.data.network

import com.hitenderpannu.auth.data.network.entity.LoginRequest
import com.hitenderpannu.auth.data.network.entity.LoginResponse
import com.hitenderpannu.auth.data.network.entity.NetworkUser
import com.hitenderpannu.auth.data.network.entity.SignUpRequest
import com.hitenderpannu.auth.data.network.entity.SignupResponse
import com.hitenderpannu.common.entity.NetworkResponse
import com.hitenderpannu.common.entity.Status
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthRepoImplTest {
    @Mock
    lateinit var authApi: AuthApi

    @InjectMocks
    lateinit var authRepoImpl: AuthRepoImpl

    @Test
    fun loginSuccess() {
        val mockedUser = NetworkUser("1", "test", "test@gmail.com")
        val mockedResponse = NetworkResponse(Status(200, "Success"), LoginResponse(mockedUser, "token"))

        runBlocking {
            val request = LoginRequest("test@gmail.com", "12345678")
            Mockito.`when`(authApi.login(request)).thenReturn(mockedResponse)

            val response = authRepoImpl.login("test@gmail.com", "12345678")

            Mockito.verify(authApi).login(request)

            Assert.assertEquals(response.id, mockedUser._id)
            Assert.assertEquals(response.name, mockedUser.name)
            Assert.assertEquals(response.email, mockedUser.email)
            Assert.assertEquals(response.authToken, mockedResponse.data?.token)
        }
    }

    @Test
    fun loginFailure() {
        val mockedResponse = NetworkResponse<LoginResponse>(Status(403, "Invalid Credentials"))

        runBlocking {
            val request = LoginRequest("test@gmail.com", "12345678")
            Mockito.`when`(authApi.login(request)).thenReturn(mockedResponse)
            try {
                val response = authRepoImpl.login("test@gmail.com", "12345678")
            } catch (exception: Exception) {
                Assert.assertEquals(exception.message, "Invalid Credentials")
            }
            Mockito.verify(authApi).login(request)
        }
    }

    @Test
    fun logoutSuccess() {
        runBlocking {
            val mockedResponse = NetworkResponse(Status(200, "Success"), null)
            Mockito.`when`(authApi.logout()).thenReturn(mockedResponse)

            authRepoImpl.logout()

            Mockito.verify(authApi).logout()
        }
    }

    @Test
    fun logoutFailure() {
        runBlocking {
            val mockedResponse = NetworkResponse(Status(404, "Token Invalid"), null)
            Mockito.`when`(authApi.logout()).thenReturn(mockedResponse)
            try {
                authRepoImpl.logout()
            } catch (exception: Exception) {
                Assert.assertEquals(exception.message, "Token Invalid")
            }
            Mockito.verify(authApi).logout()
        }
    }

    @Test
    fun signupSuccess() {
        val mockedUser = NetworkUser("1", "test", "test@gmail.com")
        val mockedResponse = NetworkResponse(Status(200, "Success"), SignupResponse(mockedUser, "token"))

        runBlocking {
            val request = SignUpRequest("test", "test@gmail.com", "12345678")
            Mockito.`when`(authApi.signup(request)).thenReturn(mockedResponse)

            val response = authRepoImpl.signup("test", "test@gmail.com", "12345678")

            Mockito.verify(authApi).signup(request)

            Assert.assertEquals(response.id, mockedUser._id)
            Assert.assertEquals(response.name, mockedUser.name)
            Assert.assertEquals(response.email, mockedUser.email)
            Assert.assertEquals(response.authToken, mockedResponse.data?.token)
        }
    }

    @Test
    fun signupFailure() {
        val mockedResponse = NetworkResponse<SignupResponse>(Status(200, "Email already exist"))

        runBlocking {
            val request = SignUpRequest("test", "test@gmail.com", "12345678")
            Mockito.`when`(authApi.signup(request)).thenReturn(mockedResponse)
            try {
                val response = authRepoImpl.signup("test", "test@gmail.com", "12345678")
            } catch (error: Exception) {
                Assert.assertEquals(error.message, "Email already exist")
            }
            Mockito.verify(authApi).signup(request)
        }
    }
}
