package ru.oliverhd.currencyconverter.repository

import retrofit2.Call
import ru.oliverhd.currencyconverter.model.ServerResponseData

interface Repository {
    fun getCurrencyList(): Call<ServerResponseData>
}