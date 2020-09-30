package com.example.daggerandroiddemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.daggerandroiddemo.models.User
import com.example.daggerandroiddemo.ui.auth.AuthResource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager @Inject constructor() {
    private val TAG = "DaggerExample"

    // data
    private val cachedUser =
        MediatorLiveData<AuthResource<User>>()

    fun authenticateWithId(source: LiveData<AuthResource<User>>){
        cachedUser.value = AuthResource.loading(null as User?)
        cachedUser.addSource(source) {
            cachedUser.value = it
            cachedUser.removeSource(source)
        }
    }

    fun logOut() {
        Log.d(TAG, "logOut: logging out...")
        cachedUser.value = AuthResource.logout<User>()
    }


    fun getAuthUser(): LiveData<AuthResource<User>> {
        return cachedUser
    }
}