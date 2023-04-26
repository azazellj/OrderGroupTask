package com.azazellj.ordergrouptask.di

import com.azazellj.ordergrouptask.data.datasource.MainDataSourceImpl
import com.azazellj.ordergrouptask.data.repository.MainRepositoryImpl
import com.azazellj.ordergrouptask.domain.datasource.MainDataSource
import com.azazellj.ordergrouptask.domain.repository.MainRepository
import com.azazellj.ordergrouptask.network.api.MainAPI
import com.azazellj.ordergrouptask.presentation.main.MainViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KoinCoreModule {

    private val apiModule = module {
        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl("https://ext-api-test.dev3.ordergroup.dk")
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder()
                            .setLenient()
                            .create()
                    )
                )
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            }
                        )
                        .build()
                )
                .build()
        }
        single<MainAPI> {
            get<Retrofit>().create(MainAPI::class.java)
        }
    }

    private val dataSourceModule = module {
        includes(apiModule)

        single<MainDataSource> {
            MainDataSourceImpl(
                mainAPI = get(),
            )
        }
    }

    private val repositoryModule = module {
        includes(dataSourceModule)

        single<MainRepository> {
            MainRepositoryImpl(
                mainDataSource = get(),
            )
        }
    }

    private val viewModelModule = module {
        includes(repositoryModule)

        viewModel {
            MainViewModel(
                mainRepository = get(),
            )
        }
    }

    val koinCoreModule = module {
        includes(viewModelModule)
    }
}