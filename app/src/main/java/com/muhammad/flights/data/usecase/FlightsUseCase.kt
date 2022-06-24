package com.muhammad.flights.data.usecase

import android.app.Application
import com.google.gson.Gson
import com.muhammad.flights.app.Constants
import com.muhammad.flights.data.model.FlightsModel
import java.io.InputStream
import javax.inject.Inject

class FlightsUseCase @Inject constructor(private val application: Application) {

    fun getFlightsUseCase(): State<FlightsModel> {
        lateinit var json: String
        return try {
            val inputStream: InputStream = application.assets.open(Constants.FILE_DIRECTORY)
            json = inputStream.bufferedReader().use { it.readText() }
            val model = Gson().fromJson(json, FlightsModel::class.java)
            State.success(model)
        } catch (e: Exception) {
            State.error("Error fetching data: ${e.message}")
        }
    }

}