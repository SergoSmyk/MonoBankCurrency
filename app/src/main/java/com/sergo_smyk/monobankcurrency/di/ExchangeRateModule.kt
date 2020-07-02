package com.sergo_smyk.monobankcurrency.di

import com.sergo_smyk.monobankcurrency.data.api.ExchangeRateApiService
import com.sergo_smyk.monobankcurrency.data.datasource.rates.RatesDataSource
import com.sergo_smyk.monobankcurrency.data.datasource.rates.RatesLocalSource
import com.sergo_smyk.monobankcurrency.data.datasource.rates.RatesRemoteSource
import com.sergo_smyk.monobankcurrency.data.repository.RatesRepository
import com.sergo_smyk.monobankcurrency.data.repository.RatesRepositoryImpl
import com.sergo_smyk.monobankcurrency.intent.ExchangeRateIntent
import com.sergo_smyk.monobankcurrency.ui.binder.RatesListBinder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(FragmentComponent::class)
@ExperimentalCoroutinesApi
abstract class ExchangeRateModule {

    @Binds
    @FragmentScoped
    abstract fun localSource(impl: RatesLocalSource): RatesDataSource.Local

    @Binds
    @FragmentScoped
    abstract fun remoteSource(impl: RatesRemoteSource): RatesDataSource.Remote

    @Binds
    @FragmentScoped
    abstract fun repository(impl: RatesRepositoryImpl): RatesRepository

    companion object {

        @Provides
        @FragmentScoped
        fun exchangeRateService(retrofit: Retrofit) = retrofit.create<ExchangeRateApiService>()

        @Provides
        @FragmentScoped
        fun rateListBinder() = RatesListBinder()

        @Provides
        @FragmentScoped
        fun intentFactory(scope: CoroutineScope, repository: RatesRepository) =
            ExchangeRateIntent(scope, repository)

        @Provides
        @FragmentScoped
        fun coroutineScope() = CoroutineScope(Dispatchers.IO)
    }
}