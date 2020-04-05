package com.hitenderpannu.taskapp.di

import android.app.Application
import com.hitenderpannu.taskapp.BuildConfig
import com.hitenderpannu.userlist.ui.di.UserListComponent
import dagger.Module
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
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
}
