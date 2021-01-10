package com.example.soa.ui.screen

import android.os.Bundle
import com.example.soa.R
import com.example.soa.ui.fragment.LoginFragment
import com.example.soa.ui.screen.base.BaseActivity

class LoginScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_container)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment().apply { arguments = intent.extras }, null).commit()
        }
    }
}