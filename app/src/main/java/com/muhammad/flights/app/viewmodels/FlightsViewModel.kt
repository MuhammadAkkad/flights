package com.muhammad.flights.app.viewmodels

import androidx.lifecycle.ViewModel
import com.muhammad.flights.data.model.FlightsModel
import com.muhammad.flights.data.usecase.FlightsUseCase
import com.muhammad.flights.data.usecase.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(private val useCase: FlightsUseCase) : ViewModel() {

    fun getFlights(): Observable<State<FlightsModel>> { // delay is intentional to mimic API call
        return Observable.just(useCase.getFlightsUseCase()).delay(2, TimeUnit.MILLISECONDS)
    }

}

