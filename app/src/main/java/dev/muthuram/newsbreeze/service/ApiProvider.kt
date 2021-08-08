package dev.muthuram.newsbreeze.service

import android.content.Context
import dev.muthuram.newsbreeze.BuildConfig
import dev.muthuram.newsbreeze.constants.API_KEY
import dev.muthuram.newsbreeze.helper.defaultValue
import dev.muthuram.newsbreeze.helper.isInternetAvailable
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiProvider: KoinComponent {

    val context: Context by inject()

    private fun customCache(): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(context.cacheDir, cacheSize)
    }

    private val onlineInterceptor = Interceptor{ chain ->
        val maxAge = 60
        val request = chain.request().newBuilder()
            .header("Authorization", API_KEY)
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
        chain.proceed(request)
    }

    val offlineInterceptor = Interceptor{ chain ->
        var request = chain.request()
        if (!context.isInternetAvailable().defaultValue()){
            val maxStale = 60 * 60 * 24 * 30
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        return@Interceptor chain.proceed(request)
    }

    private fun loggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private fun httpClient() =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor())
            addInterceptor(offlineInterceptor)
            addNetworkInterceptor(onlineInterceptor)
            cache(customCache())
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }.build()

    private val retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        client(httpClient())
    }.build()

    val client : ServiceApi by lazy { retrofit.create(ServiceApi::class.java) }

}