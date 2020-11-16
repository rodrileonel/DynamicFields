package com.appstuddio.dynamicfields.network.response

import com.google.gson.annotations.SerializedName

data class FieldResponse(
    @SerializedName("component_type") val componentType :String?,
    @SerializedName("field_name_key") val fieldKey :String?,
    @SerializedName("field_name_view") val fieldView :String?,
    @SerializedName("max") val max :String?,
    @SerializedName("min") val min:String?,
    @SerializedName("value") val value :String?,
    @SerializedName("ordinal") val ordinal :String?
)