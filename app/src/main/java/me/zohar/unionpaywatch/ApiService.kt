package me.zohar.unionpaywatch

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.POST



interface ApiService {

    @POST("/order/paid")
    fun notify(@Field("randomMoney") money: String): Observable<Any>

    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://localhost:8080/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}