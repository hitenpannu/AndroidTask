package com.hitenderpannu.userListDataNetwork

import com.hitenderpannu.userListDataNetwork.entity.NetworkUser
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
class UserListRemoteRepoTest {

    @Mock
    lateinit var userListApi: UserListApi;

    @InjectMocks
    private lateinit var userListRepo: UserListRemoteRepoImpl

    @Test
    fun `return user list from api`() {

        val listOfNetworkUser = listOf<NetworkUser>(NetworkUser("1", "email@email.com", "name"))
        val expectedUserList = listOf<User>(User("1", "email@email.com", "name"))

        runBlocking {
            Mockito.`when`(userListApi.getUserList()).thenReturn(listOfNetworkUser)

            val result = userListRepo.getUserList()

            Mockito.verify(userListApi).getUserList()

            Assert.assertEquals(result, expectedUserList)
        }
    }
}
