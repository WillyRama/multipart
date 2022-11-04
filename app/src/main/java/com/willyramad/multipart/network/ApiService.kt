package com.willyramad.multipart.network

import com.willyramad.multipart.model.ResponUserItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("Api/v1/login")
    @Multipart
    fun userRegis(
        @Part("username") name : RequestBody,
        @Part("password") password : RequestBody,
        @Part("foto") foto : MultipartBody.Part
    ):Call<ResponUserItem>
}