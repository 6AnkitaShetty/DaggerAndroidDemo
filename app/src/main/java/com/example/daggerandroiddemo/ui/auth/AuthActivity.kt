package com.example.daggerandroiddemo.ui.auth

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.example.daggerandroiddemo.R
import com.example.daggerandroiddemo.ui.main.MainActivity
import com.example.daggerandroiddemo.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var logo: Drawable

    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: AuthViewModel

    private lateinit var userId: EditText

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        userId = findViewById(R.id.user_id_input)
        progressBar = findViewById(R.id.progress_bar)
        login_button.setOnClickListener(this)
        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel::class.java)
        setLogo()
        subscribeObservers()
    }

    private fun setLogo() {
        requestManager.load(logo)
            .into(login_logo)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.login_button -> attemptLogin()
        }
    }

    private fun attemptLogin() {
        if (TextUtils.isEmpty(userId.text.toString())) {
            return
        }
        viewModel.authenticateWithId((userId.text.toString()).toInt())
    }

    private fun subscribeObservers() {
        viewModel.observeAuthState().observe(this, Observer { userAuthResource ->
            userAuthResource?.let {
                when (it.status) {
                    AuthResource.AuthStatus.LOADING -> {
                        showProgressBar(true)
                    }
                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        showProgressBar(false)
                        Log.d("AuthActivity", "Login success " + it.data?.email)
                        onLoginSuccess()
                    }
                    AuthResource.AuthStatus.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(
                            this,
                            it.message + "\nDid you enter a number between 1 and 10?",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                        showProgressBar(false)
                    }
                }
            }
        })
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun onLoginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}