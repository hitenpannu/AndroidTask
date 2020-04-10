package com.hitenderpannu.userlist.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hitenderpannu.common.utils.di.SupportInjection
import com.hitenderpannu.userlist.entity.User
import com.hitenderpannu.userlist.ui.di.DaggerManager
import javax.inject.Inject

class UserListActivity : AppCompatActivity(), SupportInjection {

    @Inject
    lateinit var viewModel: UserListViewModel

    @Inject
    lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_user_list)
        DaggerManager.inject(this)

        val userListView = findViewById<RecyclerView>(R.id.userListView)
        userListView.layoutManager = LinearLayoutManager(this)
        userListView.adapter = adapter
        startObservingLiveData()
    }

    private fun startObservingLiveData() {
        viewModel.liveUserList().observe(this, userListObserver)
        viewModel.liveError().observe(this, errorObserver)
        viewModel.liveProgress().observe(this, progressObserver)
    }

    private val userListObserver = Observer<List<User>> { userList ->
        Log.e("APP_USER", userList.size.toString())
        adapter.updateUserList(userList)
    }

    private val errorObserver = Observer<Throwable> { error ->
        Log.e("APP_ERROR", error.message)
    }

    private val progressObserver = Observer<Boolean> { progressStatus ->
        Log.e("APP_PROGRESS", progressStatus.toString())
    }

    override fun onDestroy() {
        stopObservingLiveData()
        super.onDestroy()
    }

    private fun stopObservingLiveData() {
        viewModel.liveUserList().removeObserver(userListObserver)
        viewModel.liveError().removeObserver(errorObserver)
        viewModel.liveProgress().removeObserver(progressObserver)
    }
}
