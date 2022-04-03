package com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable

import com.rjsquare.kkmt.AppConstant.ApplicationClass
import com.rjsquare.kkmt.RetrofitInstance.UnsafeOkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object ApiCallingInstance {

    private var retrofit: Retrofit? = null
    var okHttpClient: OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    val retrofitInstance: Retrofit
        get() {
//            ApplicationClass.DecodeData(BASE_URL)
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(ApplicationClass.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
}