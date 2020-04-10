package com.hitenderpannu.userlist.ui.di

import androidx.lifecycle.ViewModelProviders
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.userListDataNetwork.UserListApi
import com.hitenderpannu.userListDataNetwork.UserListRemoteRepo
import com.hitenderpannu.userListDataNetwork.UserListRemoteRepoImpl
import com.hitenderpannu.userlist.domain.UserListInteractor
import com.hitenderpannu.userlist.domain.UserListInteractorImpl
import com.hitenderpannu.userlist.ui.UserListAdapter
import com.hitenderpannu.userlist.ui.UserListActivity
import com.hitenderpannu.userlist.ui.UserListViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class UserListModule(private val userListActivity: UserListActivity) {

    @FeatureScope
    @Provides
    fun provideUserListApi(retrofit: Retrofit): UserListApi = retrofit.create(UserListApi::class.java)

    @FeatureScope
    @Provides
    fun provideUserListRemoteRepo(userListApi: UserListApi): UserListRemoteRepo = UserListRemoteRepoImpl(userListApi)

    @FeatureScope
    @Provides
    fun providerUserListInteractor(
        remoteRepo: UserListRemoteRepo,
        networkConnectionChecker: NetworkConnectionChecker
    ): UserListInteractor = UserListInteractorImpl(networkConnectionChecker, remoteRepo)

    @FeatureScope
    @Provides
    fun provideUserListAdapter(): UserListAdapter = UserListAdapter();

    @FeatureScope
    @Provides
    fun provideUserListViewModel(userListInteractor: UserListInteractor): UserListViewModel {
        val viewModel = ViewModelProviders.of(userListActivity).get(UserListViewModel::class.java)
        viewModel.userListInteractor = userListInteractor
        return viewModel
    }
}
