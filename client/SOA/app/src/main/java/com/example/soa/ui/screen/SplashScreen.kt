package com.example.soa.ui.screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.soa.SoaApplication
import com.example.soa.repository.IPreference
import com.example.soa.ui.model.ISplashViewModel
import com.example.soa.ui.model.SplashViewModel
import com.example.soa.ui.model.SplashViewModelFactory
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {

    @Inject
    internal lateinit var preference: IPreference

    private val viewModel: ISplashViewModel by lazy {
        ViewModelProvider(this, SplashViewModelFactory(preference)).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as SoaApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

        viewModel.action.observe(this, Observer {
            when (it!!) {
                SplashViewModel.Action.LOGIN -> startActivity(Intent(this, LoginScreen::class.java))
                SplashViewModel.Action.PROGRAMS -> startActivity(Intent(this, ProductsScreen::class.java))
            }
            finish()
        })
    }

}