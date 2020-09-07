package pl.kgorak.hospitalapp.api

import okhttp3.OkHttpClient
import pl.kgorak.hospitalapp.data.DataObj
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private val okHttpClient : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()


    val instance: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.146:3001")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiInterface::class.java)
    }

}