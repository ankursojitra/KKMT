package com.rjsquare.cricketscore.Retrofit2Services.MatchPointTable

import com.rjsquare.kkmt.AppConstant.Constants
import com.rjsquare.kkmt.RetrofitInstance.UnsafeOkHttpClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCallingInstance {

    private var retrofit: Retrofit? = null
    var okHttpClient: OkHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
    val retrofitInstance: Retrofit
        get() {
//            GlobalUsage.DecodeData(BASE_URL)
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
}