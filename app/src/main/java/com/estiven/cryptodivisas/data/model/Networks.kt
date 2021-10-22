package com.estiven.cryptodivisas.data.model

import com.google.gson.annotations.SerializedName

data class Networks (
    @SerializedName("network") var network : String,
    @SerializedName("protocol") var protocol : String,
    @SerializedName("default") var default : Boolean,
    @SerializedName("payin_enabled") var payinEnabled : Boolean,
    @SerializedName("payout_enabled") var payoutEnabled : Boolean,
    @SerializedName("precision_payout") var precisionPayout : Float,
    @SerializedName("payout_fee") var payoutFee : Float,
    @SerializedName("payout_is_payment_id") var payoutIsPaymentId : Boolean,
    @SerializedName("payin_payment_id") var payinPaymentId : Boolean,
    @SerializedName("payin_confirmations") var payinConfirmations : Int,
    @SerializedName("low_processing_time") var lowProcessingTime : Float,
    @SerializedName("high_processing_time") var highProcessingTime : Float,
    @SerializedName("avg_processing_time") var avgProcessingTime : Float

)
