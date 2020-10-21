package pl.polsl.workflow.manager.client.model.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import pl.polsl.workflow.manager.client.App
import pl.polsl.workflow.manager.client.model.data.AuthenticationView
import pl.polsl.workflow.manager.client.model.data.checkedToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ServiceHolder {

    private var authenticationView: AuthenticationView? = null

    fun updateToken(authenticationView: AuthenticationView) {
        this.authenticationView = authenticationView
    }

    private val okHttpClient: OkHttpClient
        get() {
            val interceptor = Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .method(original.method(), original.body())
                authenticationView?.checkedToken?.let { token ->
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                val request = requestBuilder.build()
                App.log("url = ", request.url().url())
                chain.proceed(request)
            }
            return OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        }

    private val gson: Gson
        get() {
            return GsonBuilder()
                .create()
        }

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://workflow-manager-server.herokuapp.com/workflow-manager-api/")
        //.baseUrl("http://192.168.0.220:8080/workflow-manager-api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    inline fun <reified T> get(): T {
        return retrofit.create()
    }


}