package ru.oliverhd.currencyconverter.model

import com.google.gson.annotations.SerializedName

data class ServerResponseData(
    @SerializedName("Date") val Date: String,
    @SerializedName("PreviousDate") val PreviousDate: String,
    @SerializedName("PreviousURL") val PreviousURL: String,
    @SerializedName("Timestamp") val Timestamp: String,
    @SerializedName("Valute") val Valute: Valute
)