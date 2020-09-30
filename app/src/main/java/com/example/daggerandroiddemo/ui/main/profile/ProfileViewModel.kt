package com.example.daggerandroiddemo.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.daggerandroiddemo.SessionManager
import com.example.daggerandroiddemo.models.User
import com.example.daggerandroiddemo.ui.auth.AuthResource
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val sessionManager: SessionManager) :
    ViewModel() {
    private val TAG = "ProfileViewModel"

    fun getAuthenticatedUser(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }
}