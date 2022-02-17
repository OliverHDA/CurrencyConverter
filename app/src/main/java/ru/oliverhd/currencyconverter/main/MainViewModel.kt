package ru.oliverhd.currencyconverter.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oliverhd.currencyconverter.model.Currency
import ru.oliverhd.currencyconverter.model.ServerResponseData
import ru.oliverhd.currencyconverter.model.getDefaultCurrency
import ru.oliverhd.currencyconverter.repository.Repository
import java.text.NumberFormat

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _meetingLiveData: MutableLiveData<CurrencyData> =
        MutableLiveData()
    private val meetingLiveData: LiveData<CurrencyData> = _meetingLiveData

    fun getLiveData() = meetingLiveData
    fun getCurrencies() {
        repository.getCurrencyList().enqueue(object : Callback<ServerResponseData> {
            override fun onResponse(
                call: Call<ServerResponseData>,
                response: Response<ServerResponseData>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val currencies: MutableList<Currency> = mutableListOf(getDefaultCurrency())
                    currencies.addAll(response.body()!!.Valute.currencies)
                    _meetingLiveData.postValue(CurrencyData.Success(currencies))
                } else {
                    CurrencyData.Error(Throwable(CONNECTION_ERROR))
                }
            }

            override fun onFailure(call: Call<ServerResponseData>, t: Throwable) {
                CurrencyData.Error(t)
            }
        })
    }

    fun getConverted(
        startSum: String,
        startCurrencyValue: String,
        endCurrencyValue: String,
        endCurrencyCode: String
    ): String {
        val answer: Double =
            startSum.toDouble() * startCurrencyValue.toDouble() / endCurrencyValue.toDouble()
        val currency: java.util.Currency = java.util.Currency.getInstance(endCurrencyCode)
        val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.currency = currency
        return numberFormat.format(answer)
    }

    companion object {
        private const val CONNECTION_ERROR = "Ошибка соединения"
    }
}