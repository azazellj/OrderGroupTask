package com.azazellj.ordergrouptask.network.api

import com.azazellj.ordergrouptask.network.response.QueryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPI {

    @GET("android_test/test.php")
    suspend fun fetchQuery(
        @Query(value = "query") query: String,
    ): QueryResponse
}