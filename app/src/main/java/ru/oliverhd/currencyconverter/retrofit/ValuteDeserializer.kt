package ru.oliverhd.currencyconverter.retrofit

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.oliverhd.currencyconverter.model.Currency
import ru.oliverhd.currencyconverter.model.Valute
import java.lang.reflect.Type

class ValuteDeserializer : JsonDeserializer<Valute> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Valute {
        val jsonObject = json!!.asJsonObject
        val currencies: MutableList<Currency> = mutableListOf()
        val set = jsonObject
            .entrySet()
        val it: Iterator<Map.Entry<String, JsonElement>> = set.iterator()
        while (it.hasNext()) {
            val currency = Gson().fromJson(it.next().value, Currency::class.java)
            currencies.add(currency)
        }
        return Valute(currencies)
    }
}