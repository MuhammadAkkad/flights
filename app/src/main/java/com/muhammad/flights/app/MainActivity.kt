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


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: FlightsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getFlights().observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .subscribe { onNext ->
                when (onNext.status) {
                    Status.ERROR -> {
                        hideLoading()
                    }
                    Status.SUCCESS -> {
                        onNext.data?.let { setupAdapter(it) }
                        hideLoading()
                    }
                    else -> {
                        hideLoading()
                    }
                }
            }


    }

    private fun setupAdapter(data: FlightsModel) {
        val adapter = FlightsAdapter(data, applicationContext)
        binding.flightsRv.layoutManager = LinearLayoutManager(applicationContext)
        binding.flightsRv.adapter = adapter
    }

    private fun showLoading() {
        binding.pb.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pb.visibility = View.GONE
    }

}