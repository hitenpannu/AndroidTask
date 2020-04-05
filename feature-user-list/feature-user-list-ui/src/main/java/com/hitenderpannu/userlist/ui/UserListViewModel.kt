package com.hitenderpannu.userlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.userlist.domain.UserListInteractor
import com.hitenderpannu.userlist.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListViewModel(
    private val userListInteractor: UserListInteractor
) : ViewModel() {

    private val mutableUserList = MutableLiveData<List<User>>()
    private val mutableError = MutableLiveData<Throwable>()
    private val mutableProgress = MutableLiveData<Boolean>()

    fun liveUserList() = mutableUserList as LiveData<List<User>>
    fun liveError() = mutableError as LiveData<Throwable>
    fun liveProgress() = mutableProgress as LiveData<Boolean>

    init {
        fetchUserList()
    }

    fun fetchUserList() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableProgress.postValue(true)
            try {
                val userList = userListInteractor.getListOfAllUsers()
                mutableUserList.postValue(userList);
            } catch (error: Throwable) {
                mutableError.postValue(error)
            } finally {
                mutableProgress.postValue(false)
            }
        }
    }
}
