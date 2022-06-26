package com.muhammad.flights.app

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammad.flights.R
import com.muhammad.flights.app.adapters.FlightsAdapter
import com.muhammad.flights.app.viewmodels.FlightsViewModel
import com.muhammad.flights.data.model.FlightsModel
import com.muhammad.flights.data.usecase.Status
import com.muhammad.flights.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.apache.commons.text.StringEscapeUtils
import kotlin.math.ceil


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: FlightsViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // disable night mode.

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
                            configureViews(true)
                        }
                    }
                }
            }
    }

    private fun setDataToViews(model: FlightsModel) {
        binding.toolBarLayout.originCountryNameTv.text =
            StringEscapeUtils.unescapeJava(model.data.search_parameters.origin.city_name)

        binding.toolBarLayout.destinationCountryNameTv.text =
            StringEscapeUtils.unescapeJava(model.data.search_parameters.destination.city_name)

        binding.toolBarLayout.numberOfPassengersTv.text =
            model.data.search_parameters.passenger_count.toString()

        val flightRoutType =
            if (model.data.search_parameters.is_one_way) getString(R.string.one_way) else getString(
                R.string.round_trip
            )
        binding.toolBarLayout.flightRouteTypeTv.text = flightRoutType

        binding.toolBarLayout.numberOfPassengersTv.text =
            model.data.search_parameters.passenger_count.toString()

        // price
        val price =
            Utils.formatCurrency(ceil(model.data.flights.departure[0].price_breakdown.total))
        val currency = model.data.flights.departure[0].price_breakdown.displayed_currency
        val valueToDisplay = "$price $currency"
        binding.flightsOtherDatesLayout.todayPriceTv.text = valueToDisplay

        val previousDayPrice =
            Utils.formatCurrency(ceil(model.data.price_history.departure.previous_day_price.toDouble()))
        val previousDayPriceWithCurrency = "$previousDayPrice $currency"
        binding.flightsOtherDatesLayout.previousDayPriceTv.text = previousDayPriceWithCurrency

        val nextDayPrice =
            Utils.formatCurrency(ceil(model.data.price_history.departure.next_day_price.toDouble()))
        val nextDayPriceWithCurrency = "$nextDayPrice $currency"
        binding.flightsOtherDatesLayout.nextDayPriceTv.text = nextDayPriceWithCurrency

        // departure date
        val date = model.data.search_parameters.departure_date
        binding.flightsOtherDatesLayout.todayDateTv.text = viewModel.getCustomDateScoreboard(date)
    }

    private fun setupAdapter(data: FlightsModel) {
        handler.postDelayed({
            val adapter = FlightsAdapter(data, applicationContext)
            binding.flightsRv.layoutManager = LinearLayoutManager(applicationContext)
            binding.flightsRv.adapter = adapter
        }, 100)
    }

    private fun configureViews(isLoaded: Boolean) {
        handler.post {
            binding.pb.visibility = if (isLoaded) View.GONE else View.VISIBLE
            binding.toolBarLayout.toolbarFlightInfoLayout.visibility =
                if (!isLoaded) View.GONE else View.VISIBLE
            binding.flightsOtherDatesLayout.root.visibility =
                if (!isLoaded) View.GONE else View.VISIBLE
            binding.buttonsLayout.visibility = if (!isLoaded) View.GONE else View.VISIBLE
            binding.flightsRv.visibility = if (!isLoaded) View.GONE else View.VISIBLE
        }
    }
}