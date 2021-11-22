package eu.andreihaiu.task.util.extensions

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import eu.andreihaiu.task.BuildConfig
import eu.andreihaiu.task.util.constants.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientProvider {
    fun provideBuilder(
        context: Context,
        authToken: String?
    ): OkHttpClient.Builder {

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)

        builder.addNetworkInterceptor(provideAuthInterceptor(authToken))

        if (BuildConfig.DEBUG) builder.addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(CHUCKER_CONTENT_LENGTH)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )

        builder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })

        return builder
    }

    private fun provideAuthInterceptor(
        authToken: String?
    ): Interceptor {
        return Interceptor { chain ->
            val request: Request = chain.request()
            val url = request
                .url
                .newBuilder()
                .build()

            val requestBuilder = request
                .newBuilder()
                .url(url)
                .addHeader(ACCEPT_HEADER_KEY, JSON_HEADER_VALUE)


            authToken?.let {
                requestBuilder.addHeader(AUTH_HEADER_KEY, it)
            }

            chain.proceed(requestBuilder.build())
        }
    }
}