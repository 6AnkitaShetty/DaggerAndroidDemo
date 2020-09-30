package com.example.daggerandroiddemo.di

import androidx.lifecycle.ViewModelProvider
import com.example.daggerandroiddemo.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory):ViewModelProvider.Factory


}