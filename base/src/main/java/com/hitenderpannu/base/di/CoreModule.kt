package com.hitenderpannu.base.di

import com.hitenderpannu.base.BuildConfig
import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.base.network.ConnectionManagerFactory
import com.hitenderpannu.base.user.UserPreferencesImpl
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CoreModule(private val application: MainApplication) {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun provideOkHttpClient(userPreferences: UserPreferences): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .header("token", userPreferences.userToken ?: "")
                .method(originalRequest.method(), originalRequest.body())
                .build()
            chain.proceed(modifiedRequest)
        }
        return builder.build()
    }

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

    @Singleton
    @Provides
    fun provideUserPreferences(): UserPreferences {
        return UserPreferencesImpl(application);
    }
}
