package com.example.daggerandroiddemo.di.main

import com.example.daggerandroiddemo.network.main.MainApi
import com.example.daggerandroiddemo.ui.main.posts.PostRecyclerAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideAdapter(): PostRecyclerAdapter {
        return PostRecyclerAdapter()
    }

    @MainScope
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}