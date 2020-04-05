package com.hitenderpannu.userlist.ui

import android.content.Context
import androidx.fragment.app.Fragment
import com.hitenderpannu.common.utils.di.SupportInjection
import com.hitenderpannu.userlist.ui.di.UserListComponentProvider

class UserListFragment : Fragment(), SupportInjection {

    private var injectDependencies: ((Context) -> Unit)? = fun(context: Context) {
        if (context is UserListComponentProvider) {
            context.inject(this)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDependencies?.invoke(context)
        injectDependencies = null
    }
}
