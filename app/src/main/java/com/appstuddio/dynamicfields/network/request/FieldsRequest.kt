package com.appstuddio.dynamicfields.network.request

import com.google.gson.annotations.SerializedName

data class FieldsRequest(
    @SerializedName("elements") val elements:Elements,
    @SerializedName("id") val id:Int
){
    data class Elements(
        @SerializedName("description") val description:Any
    )
}