package com.example.randomusers.network

import com.example.randomusers.Util.Constant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceClient {
    private var retrofit: Retrofit? = null

    fun apiClient():Retrofit{
        val gson= GsonBuilder()
            .setLenient()
            .create()
        val interpolator=HttpLoggingInterceptor()
        interpolator.apply { interpolator.level=HttpLoggingInterceptor.Level.BODY }

        val httpClient = OkHttpClient.Builder().readTimeout(200,TimeUnit.SECONDS)
            .writeTimeout(200,TimeUnit.SECONDS)
            .connectTimeout(200,TimeUnit.SECONDS).addInterceptor(interpolator).build()

        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}