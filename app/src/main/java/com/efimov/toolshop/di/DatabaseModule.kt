package com.efimov.toolshop.di

import android.content.Context
import androidx.room.Room
import com.efimov.toolshop.data.local.db.AppDatabase
import com.efimov.toolshop.data.local.db.AppDao
import com.efimov.toolshop.data.repository.CartRepositoryImpl
import com.efimov.toolshop.data.repository.CategoryRepositoryImpl
import com.efimov.toolshop.data.repository.OrderRepositoryImpl
import com.efimov.toolshop.data.repository.PaymentRepositoryImpl
import com.efimov.toolshop.data.repository.ProductRepositoryImpl
import com.efimov.toolshop.data.repository.UserRepositoryImpl
import com.efimov.toolshop.domain.repository.CartRepository
import com.efimov.toolshop.domain.repository.CategoryRepository
import com.efimov.toolshop.domain.repository.OrderRepository
import com.efimov.toolshop.domain.repository.PaymentRepository
import com.efimov.toolshop.domain.repository.ProductRepository
import com.efimov.toolshop.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {

    @Binds
    @Singleton
    fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @Singleton
    fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    fun bindPaymentRepository(
        impl: PaymentRepositoryImpl
    ): PaymentRepository

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tool_db"
        )
            .build()
    }

    @Provides
    fun provideCartDao(db: AppDatabase): AppDao = db.cartDao()
}