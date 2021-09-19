package com.theguardian.data.di

import com.squareup.moshi.Moshi
import com.theguardian.data.dataservice.apiservice.search.SearchService
import com.theguardian.data.utils.HeaderInterceptor
import com.theguardian.data.utils.MAIN_URL
import com.theguardian.data.utils.QueryParamsInterceptor
import com.theguardian.data.utils.TIMEOUT_TIME_SECONDS
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private val QualifierJsonConverter = StringQualifier("JsonConverter")
private val QualifierScalarConverter = StringQualifier("ScalarConverter")
internal val QualifierLoggingInterceptor = StringQualifier("LoggingInterceptor")
internal val QualifierQueryParamInterceptor = StringQualifier("QueryParamInterceptor")
internal val QualifierHeaderInterceptor = StringQualifier("HeaderInterceptor")
internal val QualifierPublicClient = StringQualifier("PublicClient")
internal val QualifierPublicRetrofit = StringQualifier("PublicRetrofit")

val httpModule = module {
    /* Converter */
    single {
        Moshi.Builder().build()
    }
    factory<Converter.Factory>(QualifierJsonConverter) { MoshiConverterFactory.create() }
    factory<Converter.Factory>(QualifierScalarConverter) { ScalarsConverterFactory.create() }

    /* Interceptors */
    factory<Interceptor>(QualifierLoggingInterceptor) {
        HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY) }
    factory<Interceptor>(QualifierHeaderInterceptor) { HeaderInterceptor() }
    factory<Interceptor>(QualifierQueryParamInterceptor) { QueryParamsInterceptor() }

    /* Http support */

    factory(QualifierPublicClient) {
        OkHttpClient.Builder()
            .readTimeout(TIMEOUT_TIME_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_TIME_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(get<Interceptor>(QualifierHeaderInterceptor))
            .addInterceptor(get<Interceptor>(QualifierLoggingInterceptor))
            .addInterceptor(get<Interceptor>(QualifierQueryParamInterceptor))
            .build()
    }

    single(QualifierPublicRetrofit) { retrofitClientFactory(QualifierPublicClient) }

    /* Services */
    single { get<Retrofit>(QualifierPublicRetrofit).create(SearchService::class.java) }

}
private fun Scope.retrofitClientFactory(qualifier: Qualifier): Retrofit {
    return Retrofit.Builder()
        .baseUrl(MAIN_URL)
        .addConverterFactory(get(QualifierScalarConverter))
        .addConverterFactory(get(QualifierJsonConverter))
        .client(get(qualifier))
        .build()
}