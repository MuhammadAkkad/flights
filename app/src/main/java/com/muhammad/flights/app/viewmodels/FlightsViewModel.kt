package com.muhammad.flights.app.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhammad.flights.app.Constants.Companion.DISPLAY_FORMAT_PATTERN
import com.muhammad.flights.app.Constants.Companion.LANG_CODE_TR
import com.muhammad.flights.app.Constants.Companion.RESPONSE_FORMAT_PATTERN
import com.muhammad.flights.data.model.FlightsModel
import com.muhammad.flights.data.usecase.FlightsUseCase
import com.muhammad.flights.data.usecase.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(private val useCase: FlightsUseCase) : ViewModel() {

    val flightsLiveData = MutableLiveData<State<FlightsModel>>()

    fun getFlights() {
        useCase.getFlightsUseCase().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { flightsLiveData.postValue(State.loading()) }
            .doOnError { flightsLiveData.postValue(State.error(it.message.toString())) }
            .subscribe({ result ->
                flightsLiveData.postValue(result)
            }, { throwable ->
                flightsLiveData.postValue(State.error(throwable.message.toString()))
            })
    }

    fun getCustomDateScoreboard(dateTimeStr: String): String {
        val date: Date?
        lateinit var formattedTime: String
        lateinit var dayName: String
        try {
            date =
                SimpleDateFormat(RESPONSE_FORMAT_PATTERN, Locale(LANG_CODE_TR)).parse(dateTimeStr)
            val symbols = DateFormatSymbols(Locale(LANG_CODE_TR))
            val calendar = Calendar.getInstance()
            calendar.time = date
            dayName = symbols.shortWeekdays[calendar.get(Calendar.DAY_OF_WEEK)]
            formattedTime =
                SimpleDateFormat(DISPLAY_FORMAT_PATTERN, Locale(LANG_CODE_TR)).format(date)
        } catch (e: ParseException) {
            return dateTimeStr
        }
        return "$formattedTime $dayName"
    }

}

