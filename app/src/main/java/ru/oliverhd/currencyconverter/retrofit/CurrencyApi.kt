package ru.oliverhd.currencyconverter.retrofit

import retrofit2.Call
import retrofit2.http.GET
import ru.oliverhd.currencyconverter.model.ServerResponseData

interface CurrencyApi {
    @GET(value = "daily_json.js")
    fun getCurrencyList(): Call<ServerResponseData>
}