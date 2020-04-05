package com.hitenderpannu.userlist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hitenderpannu.userlist.domain.UserListInteractor
import com.hitenderpannu.userlist.entity.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var userListInteractor: UserListInteractor

    @InjectMocks
    lateinit var viewModel: UserListViewModel

    @Test
    fun `should update error live data on getting error`() {
        runBlocking {
            // Arrange
            val expectedError = Throwable("Exception")
            Mockito.`when`(userListInteractor.getListOfAllUsers())
                .thenThrow(expectedError)

            // Act
            viewModel.fetchUserList()

            // Assert
            val errorHandler = fun(error: Throwable) {
                Assert.assertEquals(expectedError, error)
            }
            val progressHandler = fun(status: Boolean) {
                Assert.assertEquals(false, status)
            }
            with(OneTimeObserver(errorHandler)) {
                viewModel.liveError().observe(this, this)
            }
            with(OneTimeObserver(progressHandler)) {
                viewModel.liveProgress().observe(this, this)
            }
        }
    }

    @Test
    fun `should update user list on success`(){
        runBlocking {
            // Arrange
            val expectedUserList = listOf<User>(User("1","test@email.com","testUser"))
            Mockito.`when`(userListInteractor.getListOfAllUsers())
                .thenReturn(expectedUserList)

            // Act
            viewModel.fetchUserList()

            // Assert
            val userListHandler = fun(newList: List<User>) {
                Assert.assertEquals(expectedUserList, newList)
            }
            val progressHandler = fun(status: Boolean) {
                Assert.assertEquals(false, status)
            }
            val errorHandler = fun(error: Throwable) {
                Assert.assertEquals(null, error)
            }
            with(OneTimeObserver(userListHandler)) {
                viewModel.liveUserList().observe(this, this)
            }
            with(OneTimeObserver(progressHandler)) {
                viewModel.liveProgress().observe(this, this)
            }
            with(OneTimeObserver(errorHandler)) {
                viewModel.liveError().observe(this, this)
            }
        }
    }
}
