package com.hitenderpannu.userListDataNetwork

import com.hitenderpannu.userListDataNetwork.entity.NetworkUser
import com.hitenderpannu.userlist.entity.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import javax.swing.plaf.basic.BasicInternalFrameTitlePane

@RunWith(MockitoJUnitRunner::class)
class UserListRepoTest {

    @Mock
    lateinit var userListApi: UserListApi;

    @InjectMocks
    private lateinit var userListRepo: UserListRepoImpl

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
