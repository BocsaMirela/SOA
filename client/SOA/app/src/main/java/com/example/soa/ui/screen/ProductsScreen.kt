package com.example.soa.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.soa.ui.fragment.ProductsFragment
import com.example.soa.R

class ProductsScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_inset_container)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ProductsFragment().apply { arguments = intent.extras }, null).commit()
        }
    }
}