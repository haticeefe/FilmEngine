package com.example.filmengine.data.remote

import com.example.filmengine.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Bu sınıf, ağ (network) işlemleri için gerekli nesneleri oluşturur.
// Oluşturulan nesneler uygulama boyunca tek bir kez kullanılır.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // OkHttpClient oluşturuyor.
    // API isteklerini Logcat'te görebilmek için loglama ekleniyor.
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    // Retrofit nesnesini oluşturuyor.
    // OkHttpClient kullanılarak API bağlantısı hazırlanıyor.
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // TmdbApiService oluşturuyor.
    // Böylece API isteklerini yapabiliyoruz.
    @Provides
    @Singleton
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }
}