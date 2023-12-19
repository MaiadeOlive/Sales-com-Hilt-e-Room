package com.oliveira.maia.app.di

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.room.Room
import com.oliveira.maia.app.data.AppDatabase
import com.oliveira.maia.app.data.dataSource.SaleDao
import com.oliveira.maia.app.domain.model.SaleEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideSaleDao(database: AppDatabase): SaleDao {
        return database.saleDao()
    }

    @Provides
    fun provideNavController(activity: ComponentActivity): NavController {
        return NavHostController(activity)
    }

    @Provides
    fun provideSaleEntityList(): List<SaleEntity> {
        return emptyList()
    }
}
