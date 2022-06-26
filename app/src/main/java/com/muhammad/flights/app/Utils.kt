package com.muhammad.flights.app

import java.text.NumberFormat
import java.util.*

class Utils {

    companion object {
        fun formatCurrency(money: Double): String? {
            return try {
                val formatter: NumberFormat = NumberFormat.getInstance(Locale("TR"))
                formatter.format(money)
            }catch (e : Exception){
                money.toString()
            }
        }

    }
}