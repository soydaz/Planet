package com.example.planet.core.network

import com.example.planet.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebServices {

    companion object {

        private const val URL = "https://swapi.dev"

        private var instance: Retrofit? = null

        private val okHttpClientBuilder: OkHttpClient.Builder
            get() = OkHttpClient.Builder().apply {
                readTimeout(90, TimeUnit.SECONDS)
                callTimeout(90, TimeUnit.SECONDS)
                connectTimeout(90, TimeUnit.SECONDS)
            }

        private val loggingInterceptor by lazy {
            LoggingInterceptor.Builder().run builder@{
                addHeader("Version", BuildConfig.VERSION_NAME)
                if (BuildConfig.DEBUG) {
                    setLevel(Level.BASIC)
                    setLevel(Level.HEADERS)
                    setLevel(Level.BASIC)
                    request("Request")
                    response("Response")
                } else
                    setLevel(Level.NONE)
                log(Platform.INFO)
                return@builder build()
            }
        }

       @Synchronized
       fun getInstance(): Retrofit {
           val okHttpClient: OkHttpClient = okHttpClientBuilder.run okHttp@{
               addInterceptor { chain ->
                   val requestBuilder = chain.request().newBuilder()
                   val headers: Headers = Headers.Builder().run {
                       add("Accept", "application/json")
                       add("Content-Type", "application/json")
                       build()
                   }
                   requestBuilder.headers(headers)
                   chain.proceed(requestBuilder.build())
               }
               addInterceptor(loggingInterceptor)
               return@okHttp build()
           }

           if (instance == null) {
               instance = Retrofit.Builder().run {
                   client(okHttpClient)
                   baseUrl(URL)
                   addConverterFactory(GsonConverterFactory.create(GsonUtils.gsonForDeserialization()))
                   build()
               }
           }
           return instance as Retrofit
       }

    }

}