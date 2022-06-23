package com.muhammad.flights.data.usecase

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muhammad.flights.app.Constats
import com.muhammad.flights.data.model.FlightsModel
import java.io.InputStream
import javax.inject.Inject

class FlightsUseCase @Inject constructor(private val application: Application) {


    fun getFlightsUseCase() : FlightsModel{
        val type = object : TypeToken<FlightsModel>() {}.type
        return  Gson().fromJson(readJSONFromAsset(), type) // to return
    }

    private fun readJSONFromAsset(): String {
        lateinit var json: String
        try {
            val inputStream: InputStream = application.assets.open(Constats.FILE_DIRECTORY)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            return "Error loading file!"
        }
        return json
    }
}