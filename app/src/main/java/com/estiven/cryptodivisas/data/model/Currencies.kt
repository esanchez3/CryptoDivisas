package com.estiven.cryptodivisas.data.model

import com.google.gson.annotations.SerializedName

data class Currencies(
    @SerializedName("full_name") var fullName: String,
    @SerializedName("payin_enabled") var payinEnabled: Boolean,
    @SerializedName("payout_enabled") var payoutEnabled: Boolean,
    @SerializedName("transfer_enabled") var transferEnabled: Boolean,
    @SerializedName("precision_transfer") var precisionTransfer: String,
    @SerializedName("networks") var networks: List<Networks>
)

