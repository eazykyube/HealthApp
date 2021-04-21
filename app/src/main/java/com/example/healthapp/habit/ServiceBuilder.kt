package com.example.healthapp.habit

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val URL = "https://keep-in-touch.tk/api/habit/"

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val dispatcher = Dispatcher()

    private val okHttp = OkHttpClient.Builder().addInterceptor(logger).dispatcher(dispatcher)

    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        dispatcher.maxRequests = 1
        return retrofit.create(serviceType)
    }
}