package com.oliveira.maia.app.core.di

import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.oliveira.maia.app.core.domain.model.ProductEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNavController(activity: ComponentActivity): NavController {
        return NavHostController(activity)
    }

    @Provides
    fun provideSaleEntityList(): List<ProductEntity> {
        return emptyList()
    }
}
