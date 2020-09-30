package com.example.daggerandroiddemo.di.main

import com.example.daggerandroiddemo.ui.main.posts.PostsFragment
import com.example.daggerandroiddemo.ui.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributePostsFragment(): PostsFragment
}