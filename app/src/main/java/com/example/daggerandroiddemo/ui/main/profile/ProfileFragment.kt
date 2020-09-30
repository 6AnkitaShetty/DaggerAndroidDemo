package com.example.daggerandroiddemo.ui.main.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.daggerandroiddemo.R
import com.example.daggerandroiddemo.models.User
import com.example.daggerandroiddemo.ui.auth.AuthResource
import com.example.daggerandroiddemo.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {
    private val TAG = "ProfileFragment"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: ProfileViewModel
    private lateinit var email: TextView
    private lateinit var username: TextView
    private lateinit var website: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = view.findViewById(R.id.email)
        username = view.findViewById(R.id.username)
        website = view.findViewById(R.id.website)
        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel::class.java)
        subscribeObservers()
    }

    fun subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(viewLifecycleOwner)
        viewModel.getAuthenticatedUser().observe(viewLifecycleOwner, Observer { authResource ->
            when (authResource.status) {
                AuthResource.AuthStatus.AUTHENTICATED -> {
                    setUserDetails(authResource.data)
                }
                AuthResource.AuthStatus.ERROR -> {
                    setErrorDetails(authResource.message)
                }
            }

        })
    }

    private fun setErrorDetails(message: String?) {
        email.text = message
        username.text = "error"
        website.text = "error"
    }

    private fun setUserDetails(user: User?) {
        user?.let {
            email.text = it.email
            username.text = it.username
            website.text = it.website
        }

    }
}