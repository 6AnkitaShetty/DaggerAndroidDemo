package com.example.daggerandroiddemo.ui.main.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.daggerandroiddemo.SessionManager
import com.example.daggerandroiddemo.models.Post
import com.example.daggerandroiddemo.network.main.MainApi
import com.example.daggerandroiddemo.ui.main.Resource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class PostsViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val mainApi: MainApi
) :
    ViewModel() {
    private val TAG = "ProfileViewModel"

    private var posts: MediatorLiveData<Resource<List<Post>>>? = null

    fun observePosts(): LiveData<Resource<List<Post>>> {
        if (posts == null) {
            posts = MediatorLiveData()
            posts!!.value = Resource.loading(null as List<Post>?)
            val id: Int = sessionManager.getAuthUser().value!!.data!!.id
            val source: LiveData<Resource<List<Post>>> =
                LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPostsFromUser(id)
                        .onErrorReturn {
                            val post = Post(-1, -1, "null", "null")
                            return@onErrorReturn listOf(post)
                        }
                        .map {
                            if (it.isNotEmpty()) {
                                if (it[0].id == -1) {
                                    Resource.error("Something went wrong", null)
                                }
                            }
                            return@map Resource.success(it)
                        }

                        .subscribeOn(Schedulers.io())
                )
            posts!!.addSource(source) {
                posts!!.value = it
                posts!!.removeSource(source)

            }
        }
        return posts!!
    }

}
