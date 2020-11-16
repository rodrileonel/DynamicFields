package com.appstuddio.dynamicfields

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appstuddio.dynamicfields.network.Client
import com.appstuddio.dynamicfields.network.request.FieldsRequest
import com.appstuddio.dynamicfields.network.response.BaseResponse
import com.appstuddio.dynamicfields.network.response.ListFieldsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository{
    private lateinit var fields: MutableLiveData<BaseResponse<ListFieldsResponse>>
    private lateinit var response: MutableLiveData<BaseResponse<String>>

    fun getData(): LiveData<BaseResponse<ListFieldsResponse>> {
        fields = MutableLiveData()
        val call: Call<BaseResponse<ListFieldsResponse>> = Client.instance!!.getForm()
        call.enqueue(object : Callback<BaseResponse<ListFieldsResponse>> {
            override fun onResponse(call: Call<BaseResponse<ListFieldsResponse>>, response: Response<BaseResponse<ListFieldsResponse>>) {
                if(response.isSuccessful){
                    fields.value = response.body()
                    Log.i("students","datos correctos")
                }
                else
                    Log.e("students","error al obtener los datos")
            }
            override fun onFailure(call: Call<BaseResponse<ListFieldsResponse>>, t: Throwable) {
                Log.e("students","error feo")
            }
        })
        return fields
    }

    fun setData(request: FieldsRequest): LiveData<BaseResponse<String>> {
        response = MutableLiveData()
        val call: Call<BaseResponse<String>> = Client.instance!!.setForm(request)
        call.enqueue(object : Callback<BaseResponse<String>> {
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    Log.i("students","datos correctos")
                }
                else
                    Log.e("students","error al obtener los datos")
            }
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                Log.e("students","error feo")
            }
        })
        return response
    }
}