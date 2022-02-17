package ru.oliverhd.currencyconverter.repository

import retrofit2.Call
import ru.oliverhd.currencyconverter.model.ServerResponseData
import ru.oliverhd.currencyconverter.retrofit.RetrofitImpl

class RepositoryImpl(private val retrofitImpl: RetrofitImpl) : Repository {
    override fun getCurrencyList(): Call<ServerResponseData> =
        retrofitImpl.getRetrofitImpl().getCurrencyList()
}