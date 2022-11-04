package com.willyramad.multipart.model


import com.google.gson.annotations.SerializedName

data class ResponUserItem(
    @SerializedName("foto")
    val foto: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)