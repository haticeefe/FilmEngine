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

// @Module: Bu sınıfın Hilt'e "nesne nasıl oluşturulur" bilgisi sağladığını belirtir.
// @InstallIn(SingletonComponent::class): Buradaki nesnelerin uygulama boyunca (Application yaşam
// döngüsü boyunca) TEK bir örneğinin olacağını, yani Singleton davranacağını söyler.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Bu fonksiyon çağrıldığında (biri OkHttpClient isteyince), loglama özelliği eklenmiş
    // bir OkHttpClient döndürür. @Singleton, bu nesnenin sadece bir kez oluşturulup
    // tekrar tekrar aynısının kullanılacağını garanti eder.
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

    // Biri Retrofit isteyince, bu fonksiyon devreye girer. Dikkat: parametre olarak
    // OkHttpClient alıyor -- Hilt, yukarıdaki provideOkHttpClient() fonksiyonunu otomatik
    // çağırıp buraya "enjekte ediyor". Biz elle bağlamıyoruz, Hilt kendisi eşleştiriyor.
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Biri TmdbApiService isteyince bu fonksiyon Retrofit
    // nesnesinden gerçek API servisini üretip verir.
    @Provides
    @Singleton
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }
}