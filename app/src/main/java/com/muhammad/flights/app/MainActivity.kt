package com.muhammad.flights.app

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.muhammad.flights.app.viewmodels.FlightsViewModel
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
                        binding.hitv.text = onNext.message
                    }
                    Status.SUCCESS -> {
                        binding.hitv.text = onNext.data?.data.toString()
                        hideLoading()
                    }
                    else -> {
                        hideLoading()
                    }
                }
            }


    }

    private fun showLoading() {
        binding.pb.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pb.visibility = View.GONE
    }

}