package com.thorkane.playground

import com.thorkane.playground.login.LoginManager
import com.thorkane.playground.login.impl.LoginManagerImpl
import com.thorkane.playground.navigation.LoggedInNavigator
import com.thorkane.playground.navigation.impl.LoggedInNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindingModule {
    @Binds
    @Singleton
    abstract fun bindLoginManger(
        loginManagerImpl: LoginManagerImpl
    ): LoginManager

    @Binds
    @Singleton
    abstract fun bindLoggedInNavigator(
        loggedInNavigatorImpl: LoggedInNavigatorImpl
    ): LoggedInNavigator
}
