package ru.oliverhd.currencyconverter.main

import ru.oliverhd.currencyconverter.model.Currency

sealed class CurrencyData {
    data class Success(val currencyList: List<Currency>) : CurrencyData()
    data class Error(val error: Throwable) : CurrencyData()
}
