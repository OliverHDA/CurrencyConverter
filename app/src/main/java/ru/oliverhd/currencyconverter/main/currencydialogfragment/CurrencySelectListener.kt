package ru.oliverhd.currencyconverter.main.currencydialogfragment

import ru.oliverhd.currencyconverter.model.Currency

interface CurrencySelectListener {
    fun itemClicked(currency: Currency)
}