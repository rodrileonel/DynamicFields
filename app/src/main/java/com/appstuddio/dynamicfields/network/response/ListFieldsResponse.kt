package com.appstuddio.dynamicfields.network.response

import com.google.gson.annotations.SerializedName

data class ListFieldsResponse(
    @SerializedName("elements") val elements:List<FieldResponse>,
    @SerializedName("id") val id:Int
)