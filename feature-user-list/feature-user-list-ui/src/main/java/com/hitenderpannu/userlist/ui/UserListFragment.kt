package com.hitenderpannu.userlist.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hitenderpannu.common.utils.di.SupportInjection
import com.hitenderpannu.userlist.entity.User
import com.hitenderpannu.userlist.ui.di.UserListComponentProvider
import javax.inject.Inject

class UserListFragment : Fragment(), SupportInjection {

    @Inject
    lateinit var viewModel: UserListViewModel

    @Inject
    lateinit var adapter: UserListAdapter

    private var injectDependencies: ((Context) -> Unit)? = fun(context: Context) {
        if (context is UserListComponentProvider) {
            context.inject(this)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies?.invoke(context.applicationContext)
        injectDependencies = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userListView = view.findViewById<RecyclerView>(R.id.userListView)
        userListView.layoutManager = LinearLayoutManager(view.context)
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

    override fun onDestroyView() {
        super.onDestroyView()
        stopObservingLiveData()
    }

    private fun stopObservingLiveData() {
        viewModel.liveUserList().removeObserver(userListObserver)
        viewModel.liveError().removeObserver(errorObserver)
        viewModel.liveProgress().removeObserver(progressObserver)
    }
}
