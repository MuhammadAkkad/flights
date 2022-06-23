package com.muhammad.flights.app.viewmodels

import androidx.lifecycle.ViewModel
import com.muhammad.flights.data.model.FlightsModel
import com.muhammad.flights.data.usecase.FlightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(private val useCase: FlightsUseCase) : ViewModel() {

    fun getFlights() : FlightsModel{
       return useCase.getFlightsUseCase()
    }
}