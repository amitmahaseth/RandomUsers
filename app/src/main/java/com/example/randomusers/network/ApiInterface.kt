package com.example.randomusers.network

import com.example.randomusers.Util.Constant
import com.example.randomusers.model.RandomUserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(Constant.USERDATA)
    suspend fun getUserdata(@Query("results")results:Int):Response<RandomUserListResponse>

}