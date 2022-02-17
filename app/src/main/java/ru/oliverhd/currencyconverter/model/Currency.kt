package ru.oliverhd.currencyconverter.model

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Currency(
    @SerializedName("ID") val ID: String,
    @SerializedName("CharCode") val CharCode: String,
    @SerializedName("Name") val Name: String,
    @SerializedName("Value") val Value: Double
): Parcelable

fun getDefaultCurrency(): Currency {
    return Currency("1", "RUB", "Российский рубль", 1.0)
}
