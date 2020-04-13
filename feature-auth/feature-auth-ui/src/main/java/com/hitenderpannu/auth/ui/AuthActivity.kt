package com.hitenderpannu.auth.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hitenderpannu.auth.ui.di.DaggerManager
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val authViewModel by lazy { ViewModelProviders.of(this, viewModelFactory)[AuthViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        DaggerManager.inject(this)

        authViewModel.isAuthenticationDone.observe(this, userObserver)
    }

    private val userObserver by lazy {
        Observer<Boolean> { success ->
            if (success) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        authViewModel.isAuthenticationDone.removeObserver(userObserver)
    }
}
