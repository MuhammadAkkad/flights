package com.muhammad.flights.data.usecase

import android.app.Application
import com.google.gson.Gson
import com.muhammad.flights.app.Constants
import com.muhammad.flights.data.model.FlightsModel
import io.reactivex.rxjava3.core.Observable
import java.io.InputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FlightsUseCase @Inject constructor(private val application: Application) {

    fun getFlightsUseCase(): Observable<State<FlightsModel>> {
        lateinit var json: String
        return Observable.create<State<FlightsModel>?> {
            try {
                val inputStream: InputStream = application.assets.open(Constants.FILE_DIRECTORY)
                json = inputStream.bufferedReader().use { it.readText() }
                val model = Gson().fromJson(json, FlightsModel::class.java)
                it.onNext(State.success(model))
            } catch (e: Exception) {
                it.onError(e)
            } // delay is intentional to mimic API call
        }.delay(1000, TimeUnit.MILLISECONDS)
    }

}