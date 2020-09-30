package com.example.daggerandroiddemo.di

import com.example.daggerandroiddemo.di.auth.AuthModule
import com.example.daggerandroiddemo.di.auth.AuthScope
import com.example.daggerandroiddemo.di.auth.AuthViewModelsModule
import com.example.daggerandroiddemo.di.main.MainFragmentBuildersModule
import com.example.daggerandroiddemo.di.main.MainModule
import com.example.daggerandroiddemo.di.main.MainScope
import com.example.daggerandroiddemo.di.main.MainViewModelsModule
import com.example.daggerandroiddemo.ui.auth.AuthActivity
import com.example.daggerandroiddemo.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(modules = [AuthViewModelsModule::class, AuthModule::class])
    abstract fun contributeAuthActivity(): AuthActivity

    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class, MainViewModelsModule::class, MainModule::class])
    abstract fun contributeMainActivity(): MainActivity
}