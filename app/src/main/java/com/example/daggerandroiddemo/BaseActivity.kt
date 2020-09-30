package com.example.daggerandroiddemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.daggerandroiddemo.ui.auth.AuthActivity
import com.example.daggerandroiddemo.ui.auth.AuthResource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager.getAuthUser().observe(this, Observer { userAuthResource ->
            userAuthResource?.let {
                when (it.status) {
                    AuthResource.AuthStatus.LOADING -> {

                    }
                    AuthResource.AuthStatus.AUTHENTICATED -> {

                        Log.d("BaseActivity", "Login success " + it.data?.email)
                    }
                    AuthResource.AuthStatus.ERROR -> {

                    }
                    AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                        navLoginScreen()
                    }
                }
            }
        })
    }

   private fun navLoginScreen() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

}