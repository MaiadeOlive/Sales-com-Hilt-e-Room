package com.oliveira.maia.app.core.di

import android.content.Context
import androidx.room.Room
import com.oliveira.maia.app.core.data.dataSource.SaleDatabase
import com.oliveira.maia.app.core.data.model.ProductEntity
import com.oliveira.maia.app.core.data.model.SaleEntity
import com.oliveira.maia.app.utils.Constants.SALES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, SaleDatabase::class.java, SALES_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideSaleDao(db: SaleDatabase) = db.saleDao()

    @Provides
    fun provideEntity() = ProductEntity()

    @Provides
    fun provideSaleEntityList(): List<SaleEntity> {
        return emptyList()
    }
    @Provides
    fun provideProductEntityList(): List<ProductEntity> {
        return emptyList()
    }
}