package com.muhammad.flights.app

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammad.flights.app.adapters.FlightsAdapter
import com.muhammad.flights.app.viewmodels.FlightsViewModel
import com.muhammad.flights.data.model.FlightsModel
import com.muhammad.flights.data.usecase.Status
import com.muhammad.flights.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlin.math.ceil


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: FlightsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getFlights().observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { configureViews(false) }
            .subscribe { onNext ->
                when (onNext.status) {
                    Status.ERROR -> {
                        configureViews(false)
                    }
                    Status.SUCCESS -> {
                        onNext.data?.let {
                            setupAdapter(it)
                            setDataToViews(it)
                        }
                        configureViews(true)
                    }
                    else -> {
                        configureViews(false)
                    }
                }
            }

    }

    private fun setDataToViews(model: FlightsModel) {
        val price =
            Utils.formatCurrency(ceil(model.data.flights.departure[0].price_breakdown.total))
        val currency = model.data.flights.departure[0].price_breakdown.displayed_currency
        val valueToDisplay = "$price $currency"
        binding.flightsOtherDatesLayout.todayPriceTv.text = valueToDisplay

        val date = model.data.flights.departure[0].segments[0].departure_datetime.date
        binding.flightsOtherDatesLayout.todayDateTv.text = viewModel.getCustomDateScoreboard(date)


        val previousDayPrice =
            Utils.formatCurrency(ceil(model.data.price_history.departure.previous_day_price.toDouble()))
        val previousDayPriceWithCurrency = "$previousDayPrice $currency"
        binding.flightsOtherDatesLayout.previousDayPriceTv.text = previousDayPriceWithCurrency

        val nextDayPrice =
            Utils.formatCurrency(ceil(model.data.price_history.departure.next_day_price.toDouble()))
        val nextDayPriceWithCurrency = "$nextDayPrice $currency"
        binding.flightsOtherDatesLayout.nextDayPriceTv.text = nextDayPriceWithCurrency
    }

    private fun setupAdapter(data: FlightsModel) {
        val adapter = FlightsAdapter(data, applicationContext)
        binding.flightsRv.layoutManager = LinearLayoutManager(applicationContext)
        binding.flightsRv.adapter = adapter
    }


    private fun configureViews(isLoaded: Boolean) {
        binding.pb.visibility = if (isLoaded) View.GONE else View.VISIBLE
        binding.flightsOtherDatesLayout.root.visibility = if (!isLoaded) View.GONE else View.VISIBLE
        binding.buttonsLayout.visibility = if (!isLoaded) View.GONE else View.VISIBLE
        binding.flightsRv.visibility = if (!isLoaded) View.GONE else View.VISIBLE
    }

}