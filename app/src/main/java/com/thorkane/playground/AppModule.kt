package com.thorkane.playground

import com.thorkane.playground.login.LoginManager
import com.thorkane.playground.login.impl.LoginManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindLoginManger(
        loginManagerImpl: LoginManagerImpl
    ): LoginManager
}