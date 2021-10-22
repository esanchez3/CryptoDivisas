package com.estiven.cryptodivisas.data.model

import com.google.gson.annotations.SerializedName

data class Operations(
    @SerializedName("id") var id : Int,
    @SerializedName("price") var price : String,
    @SerializedName("qty") var qty : String,
    @SerializedName("side") var side : String,
    @SerializedName("timestamp") var timestamp : String
)
