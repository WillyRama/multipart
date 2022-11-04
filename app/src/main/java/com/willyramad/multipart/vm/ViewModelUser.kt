package com.willyramad.multipart.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.willyramad.multipart.model.ResponUserItem
import com.willyramad.multipart.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ViewModelUser : ViewModel(){

    lateinit var addUserRegis: MutableLiveData<ResponUserItem>

    init {
        addUserRegis = MutableLiveData()
    }
    fun postDataUser() : MutableLiveData<ResponUserItem>{
        return addUserRegis
    }
    fun postUser(username : RequestBody, password : RequestBody, foto : MultipartBody.Part){
        RetrofitClient.instance.userRegis(username,password,foto)
            .enqueue(object : Callback<ResponUserItem>{
                override fun onResponse(
                    call: Call<ResponUserItem>,
                    response: Response<ResponUserItem>
                ) {
                    if (response.isSuccessful){
                        addUserRegis.postValue(response.body())
                    }else{
                        addUserRegis.postValue(null)
                    }

                }

                override fun onFailure(call: Call<ResponUserItem>, t: Throwable) {
                  addUserRegis.postValue(null)
                }

            })
    }
}