package ru.oliverhd.currencyconverter.main.currencydialogfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.oliverhd.currencyconverter.databinding.ItemCurrencyListBinding
import ru.oliverhd.currencyconverter.model.Currency

class CurrencyRVAdapter(
    private val currencies: List<Currency>,
    private val selectListener: CurrencySelectListener
) : RecyclerView.Adapter<CurrencyRVAdapter.CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = ItemCurrencyListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.itemPosition = position
        holder.bind(currencies[position])
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    inner class CurrencyViewHolder(private val binding: ItemCurrencyListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var itemPosition: Int = 0

        fun bind(currency: Currency) {
            binding.root.setOnClickListener(this)
            binding.currencyName.text = currency.Name
            binding.currencyCode.text = currency.CharCode
            binding.currencyValue.text = currency.Value.toString()
        }

        override fun onClick(v: View?) {
            selectListener.itemClicked(currencies[itemPosition])
        }
    }
}