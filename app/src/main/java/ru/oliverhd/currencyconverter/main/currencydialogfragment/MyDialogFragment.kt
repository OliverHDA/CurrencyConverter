package ru.oliverhd.currencyconverter.main.currencydialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.oliverhd.currencyconverter.databinding.DialogLayoutFragmentBinding
import ru.oliverhd.currencyconverter.main.MainActivity
import ru.oliverhd.currencyconverter.main.MainActivity.Companion.START_CURRENCY
import ru.oliverhd.currencyconverter.model.Currency

class MyDialogFragment : DialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rVAdapter: CurrencyRVAdapter
    private var data: MutableList<Currency> = mutableListOf()
    private var order: Int = START_CURRENCY
    private var _binding: DialogLayoutFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val list: java.util.ArrayList<Currency>? = savedInstanceState?.getParcelableArrayList(EXTRA)
        list?.forEach {
            data.add(it)
        }
        _binding = DialogLayoutFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.currencyRecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        val listener: CurrencySelectListener = object : CurrencySelectListener {
            override fun itemClicked(currency: Currency) {
                (activity as MainActivity?)?.onCurrencySelected(currency, order)
                dialog?.dismiss()
            }
        }
        rVAdapter = CurrencyRVAdapter(data, listener)
        recyclerView.adapter = rVAdapter
    }

    fun setData(currencies: List<Currency>, order: Int) {
        data = currencies as MutableList<Currency>
        this.order = order
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA, ArrayList(data))
    }

    companion object {
        private const val EXTRA = "extra"
    }
}