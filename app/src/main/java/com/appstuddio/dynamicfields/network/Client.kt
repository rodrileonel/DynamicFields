package com.appstuddio.dynamicfields.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Client{
    private var retrofit2Api: Service? = null

    val instance: Service? by lazy {
        if (retrofit2Api == null) {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("token", "VgGTpIkiLeRY0by6Qrp42x1bk840by6Qrp")
                        .method(original.method(), original.body())
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }.build()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://potencie.com:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            retrofit2Api = retrofit.create(Service::class.java)
        }
        retrofit2Api
    }
}