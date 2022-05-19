package com.slide.test.exercise.di

import com.slide.test.core.NetworkConfig
import com.slide.test.core.TimeFormatter
import com.slide.test.core.TimeFormatterImplementation
import com.slide.test.exercise.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Stefan Halus on 18 May 2022
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNetworkConfig(): NetworkConfig {
        return NetworkConfig(
            baseUrl = BuildConfig.API_BASE_URL,
            accessToken = "331a77dc85646689d9eae82cb6e09df73c2d7600a316abcd24882ddb66b80aba"
        )
    }

    @Provides
    @Singleton
    fun provideTimeFormatter() : TimeFormatter {
        return TimeFormatterImplementation()
    }

}