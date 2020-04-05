package com.hitenderpannu.userlist.domain

import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection
import com.hitenderpannu.userListDataNetwork.UserListRemoteRepo
import com.hitenderpannu.userlist.entity.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class UserListInteractorImplTest {

    @Mock
    lateinit var networkConnectionChecker: NetworkConnectionChecker

    @Mock
    lateinit var remoteRepo: UserListRemoteRepo

    @InjectMocks
    lateinit var userListRepositoryImpl: UserListInteractorImpl

    @Test
    fun `should implement UserListRepository`() {
        Assert.assertTrue(userListRepositoryImpl is UserListInteractor)
    }

    @Test
    fun `should throw no internet connection`() {
        runBlocking {
            try {
                Mockito.`when`(networkConnectionChecker.isConnected()).thenReturn(false)
                var ignoredResult = userListRepositoryImpl.getListOfAllUsers()
            } catch (error: Throwable) {
                Assert.assertTrue(error is NoInternetConnection)
            }
        }
    }

    @Test
    fun `should provide result from network is connected to internet`() {
        runBlocking {
            // Arrange
            val expectedResult = listOf<User>(User("1", "user@test.com", "userName"))
            Mockito.`when`(remoteRepo.getUserList()).thenReturn(expectedResult)
            Mockito.`when`(networkConnectionChecker.isConnected()).thenReturn(true)

            // Act
            var result = userListRepositoryImpl.getListOfAllUsers()

            // Assert
            Mockito.verify(remoteRepo).getUserList()
            Assert.assertEquals(result, expectedResult)
        }
    }
}
