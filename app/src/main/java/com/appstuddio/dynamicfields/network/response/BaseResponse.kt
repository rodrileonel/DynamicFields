package com.appstuddio.dynamicfields.network.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("code") val code:String,
    @SerializedName("data") val data:T,
    @SerializedName("message") val message:String
)