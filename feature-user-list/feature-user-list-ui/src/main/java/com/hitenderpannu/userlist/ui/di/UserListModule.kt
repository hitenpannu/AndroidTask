package com.hitenderpannu.userlist.ui.di

import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.userListDataNetwork.UserListApi
import com.hitenderpannu.userListDataNetwork.UserListRemoteRepo
import com.hitenderpannu.userListDataNetwork.UserListRemoteRepoImpl
import com.hitenderpannu.userlist.domain.UserListInteractor
import com.hitenderpannu.userlist.domain.UserListInteractorImpl
import com.hitenderpannu.userlist.ui.UserListFragment
import dagger.Module
import retrofit2.Retrofit

@Module
class UserListModule(private val userListFragment: UserListFragment) {

    @FeatureScope
    fun provideUserListApi(retrofit: Retrofit): UserListApi = retrofit.create(UserListApi::class.java)

    @FeatureScope
    fun provideUserListRemoteRepo(userListApi: UserListApi): UserListRemoteRepo = UserListRemoteRepoImpl(userListApi)

    @FeatureScope
    fun providerUserListInteractor(
        remoteRepo: UserListRemoteRepo,
        networkConnectionChecker: NetworkConnectionChecker
    ): UserListInteractor = UserListInteractorImpl(networkConnectionChecker, remoteRepo)
}
