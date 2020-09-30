package com.example.daggerandroiddemo.ui.auth


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.example.daggerandroiddemo.SessionManager
import com.example.daggerandroiddemo.models.User
import com.example.daggerandroiddemo.network.auth.AuthApi
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AuthViewModel @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager
) : ViewModel() {


//    init {
//        Log.d(TAG, "AuthViewModel: viewmodel is working...")
//        authApi.getUser(1)
//            .toObservable()
//            .subscribeOn(Schedulers.io())
//            .subscribeBy(  // named arguments for lambda Subscribers
//                onNext = { println(it.email) },
//                onError = { it.printStackTrace() },
//                onComplete = { println("Done!") }
//            )
//    }

    fun authenticateWithId(userId: Int) {
        Log.d(TAG, "attemptLogin: attempting to login.")
        sessionManager.authenticateWithId(queryUserId(userId))
    }

    private fun queryUserId(userId: Int): LiveData<AuthResource<User>> {
        return LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(userId)
                .onErrorReturn {
                    val errorUser = User(-1, "null", "null", "null")
                    errorUser
                }
                .map {
                    if (it.id == -1) {
                        AuthResource.error("Could not authenticate", null as User)
                    }
                    AuthResource.authenticated(it)
                }
                .subscribeOn(Schedulers.io())
        )

    }

    fun observeAuthState(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}