package ru.oliverhd.currencyconverter.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ru.oliverhd.currencyconverter.databinding.ActivityMainBinding
import ru.oliverhd.currencyconverter.main.currencydialogfragment.MyDialogFragment
import ru.oliverhd.currencyconverter.model.Currency
import ru.oliverhd.currencyconverter.repository.RepositoryImpl
import ru.oliverhd.currencyconverter.retrofit.RetrofitImpl

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = MainViewModel(RepositoryImpl(RetrofitImpl()))
        viewModel.getLiveData().observe(this, Observer { renderData(it) })
        viewModel.getCurrencies()
    }

    private fun renderData(data: CurrencyData) {
        when (data) {
            is CurrencyData.Success -> {
                renderStartCurrency(data.currencyList[USD_POSITION])
                binding.startCurrency.card.setOnClickListener {
                    onCardClickListener(
                        data.currencyList,
                        START_CURRENCY
                    )
                }
                renderEndCurrency(data.currencyList[RUB_POSITION])
                binding.endCurrency.card.setOnClickListener {
                    onCardClickListener(
                        data.currencyList,
                        END_CURRENCY
                    )
                }
                binding.inputEditText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        renderConvertedCurrency(p0)
                    }
                })
            }
            is CurrencyData.Error -> {
                Toast.makeText(this, data.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onCardClickListener(currencies: List<Currency>, order: Int) {
        val myDialog = MyDialogFragment()
        myDialog.setData(currencies, order)
        myDialog.show(supportFragmentManager, CURRENCY_LIST_DIALOG_TAG)
    }

    fun onCurrencySelected(currency: Currency, order: Int) {
        when (order) {
            START_CURRENCY -> renderStartCurrency(currency)
            END_CURRENCY -> renderEndCurrency(currency)
        }
        renderConvertedCurrency(binding.inputEditText.text)
    }

    private fun renderStartCurrency(currency: Currency) {
        binding.startCurrency.currencyName.text = currency.Name
        binding.startCurrency.currencyValue.text = currency.Value.toString()
        binding.startCurrency.currencyCode.text = currency.CharCode
    }

    private fun renderEndCurrency(currency: Currency) {
        binding.endCurrency.currencyName.text = currency.Name
        binding.endCurrency.currencyValue.text = currency.Value.toString()
        binding.endCurrency.currencyCode.text = currency.CharCode
    }

    private fun renderConvertedCurrency(text: Editable?) {
        if (text.isNullOrEmpty()) {
            binding.answer.text = ENTER_AMOUNT
        } else {
            binding.answer.text = viewModel.getConverted(
                binding.inputEditText.text.toString(),
                binding.startCurrency.currencyValue.text.toString(),
                binding.endCurrency.currencyValue.text.toString(),
                binding.endCurrency.currencyCode.text.toString()
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(binding.answer.text.toString(), RESULT_EXTRA)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.answer.text = savedInstanceState.getString(RESULT_EXTRA)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val START_CURRENCY = 1
        const val END_CURRENCY = 2
        private const val RESULT_EXTRA = "result_extra"
        private const val RUB_POSITION = 0
        private const val USD_POSITION = 11
        private const val CURRENCY_LIST_DIALOG_TAG = "currency_list_dialog"
        private const val ENTER_AMOUNT = "Введите сумму"
    }
}