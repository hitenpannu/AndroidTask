package com.hitenderpannu.taskapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hitenderpannu.auth.ui.AuthFragmentDummy
import com.hitenderpannu.userlist.ui.UserListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction()
            .add(R.id.cotainer, AuthFragmentDummy())
            .commit()
    }
}
