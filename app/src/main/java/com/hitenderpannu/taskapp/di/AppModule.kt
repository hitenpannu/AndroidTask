package com.hitenderpannu.taskapp.di

import android.app.Application
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.taskapp.BuildConfig
import com.hitenderpannu.taskapp.network.ConnectionManagerFactory
import com.hitenderpannu.userlist.ui.di.UserListComponent
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    subcomponents = [
        UserListComponent::class
    ]
)
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun provideNetworkConnectionChecked(): NetworkConnectionChecker {
        val manager = ConnectionManagerFactory(application).getConnectionManager()
        return object : NetworkConnectionChecker {
            override suspend fun isConnected(): Boolean {
                return manager.isConnected()
            }
        }
    }
}
