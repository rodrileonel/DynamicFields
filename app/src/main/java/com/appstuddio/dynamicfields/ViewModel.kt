package com.appstuddio.dynamicfields

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.appstuddio.dynamicfields.network.request.FieldsRequest
import com.appstuddio.dynamicfields.network.response.BaseResponse
import com.appstuddio.dynamicfields.network.response.ListFieldsResponse

class ViewModel(application: Application): AndroidViewModel(application){

    private val repository:Repository = Repository()

    fun getData(): LiveData<BaseResponse<ListFieldsResponse>> {
        return repository.getData()
    }

    fun setData(request: FieldsRequest): LiveData<BaseResponse<String>> {
        return repository.setData(request)
    }
}