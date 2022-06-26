package com.muhammad.flights.app.viewmodels

import androidx.lifecycle.ViewModel
import com.muhammad.flights.app.Constants.Companion.DISPLAY_FORMAT_PATTERN
import com.muhammad.flights.app.Constants.Companion.LANG_CODE_TR
import com.muhammad.flights.app.Constants.Companion.RESPONSE_FORMAT_PATTERN
import com.muhammad.flights.data.model.FlightsModel
import com.muhammad.flights.data.usecase.FlightsUseCase
import com.muhammad.flights.data.usecase.State
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(private val useCase: FlightsUseCase) : ViewModel() {

    fun getFlights(): Observable<State<FlightsModel>> { // delay is intentional to mimic API call
        return Observable.just(useCase.getFlightsUseCase()).delay(1000, TimeUnit.MILLISECONDS)
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

