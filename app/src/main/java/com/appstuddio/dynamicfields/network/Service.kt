package com.appstuddio.dynamicfields.network

import com.appstuddio.dynamicfields.network.request.FieldsRequest
import com.appstuddio.dynamicfields.network.response.BaseResponse
import com.appstuddio.dynamicfields.network.response.ListFieldsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service{
    @GET("form")
    fun getForm(): Call<BaseResponse<ListFieldsResponse>>

    @POST("form")
    fun setForm(@Body request: FieldsRequest): Call<BaseResponse<String>>
}