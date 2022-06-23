package com.muhammad.flights.app.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor() : ViewModel() {
    val hi = "hello"
}