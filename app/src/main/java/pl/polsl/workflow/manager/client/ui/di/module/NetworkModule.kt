package pl.polsl.workflow.manager.client.ui.di.module

import android.content.SharedPreferences
import com.fatboyindustrial.gsonjavatime.Converters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.model.data.checkedToken
import pl.polsl.workflow.manager.client.util.token.TokenHolder
import pl.polsl.workflow.manager.client.util.token.TokenHolderImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class NetworkModule {

    @Provides
    fun provideTokenHolder(sharedPreferences: SharedPreferences, gson: Gson): TokenHolder = TokenHolderImpl(sharedPreferences, gson)

    @Provides
    fun provideOkHttpClient(tokenHolder: TokenHolder): OkHttpClient {
        val baseInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .method(original.method(), original.body())
            tokenHolder.token?.checkedToken?.let { token ->
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            val request = requestBuilder
                .build()
            //SystemClock.sleep(1000)
            App.log("url = ", request.url().url())
            chain.proceed(request)
        }
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .addInterceptor(baseInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .also { Converters.registerAll(it) }
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://workflow-manager-server.herokuapp.com/workflow-manager-api/")
            //.baseUrl("http://192.168.0.220:8080/workflow-manager-api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}