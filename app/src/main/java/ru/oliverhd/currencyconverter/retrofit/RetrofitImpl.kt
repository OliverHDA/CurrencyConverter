package ru.oliverhd.currencyconverter.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.oliverhd.currencyconverter.model.Valute

class RetrofitImpl {

    private val baseUrl = "https://www.cbr-xml-daily.ru/"

    fun getRetrofitImpl(): CurrencyApi {
        val currencyRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(createOkHttpClient())
            .build()
        return currencyRetrofit.create(CurrencyApi::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        return httpClient.build()
    }

    private val gson: Gson =
        GsonBuilder().registerTypeAdapter(Valute::class.java, ValuteDeserializer()).create()
}