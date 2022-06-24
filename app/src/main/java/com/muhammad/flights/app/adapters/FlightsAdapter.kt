package com.muhammad.flights.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhammad.flights.R
import com.muhammad.flights.data.model.Airline
import com.muhammad.flights.data.model.FlightsModel
import org.apache.commons.text.StringEscapeUtils
import java.text.NumberFormat
import java.util.*


class FlightsAdapter(private val dataSet: FlightsModel,val context: Context) :
    RecyclerView.Adapter<FlightsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val airlineIcon: ImageView
        val airlineName: TextView
        val flightPrice: TextView
        val flightType: TextView
        val departure: TextView
        val destination: TextView
        val departureTime: TextView
        val arrivalTime: TextView
        val bagagge: TextView
        val bagaggeAllawance: ImageView


        init {
            airlineIcon = view.findViewById(R.id.airline_icon_iv)
            airlineName = view.findViewById(R.id.airline_name_tv)
            flightPrice = view.findViewById(R.id.flight_price_tv)
            flightType = view.findViewById(R.id.flight_type_tv)
            departure = view.findViewById(R.id.departure_tv)
            destination = view.findViewById(R.id.destination_tv)
            departureTime = view.findViewById(R.id.departure_time_tv)
            arrivalTime = view.findViewById(R.id.arrival_time_tv)
            bagagge = view.findViewById(R.id.baggage_tv)
            bagaggeAllawance = view.findViewById(R.id.baggage_allowance_iv)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_flight, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val airlineInfo = getAirline(position)

        // airline info
        holder.airlineName.text = StringEscapeUtils.unescapeJava(airlineInfo.name)
        Glide.with(holder.airlineIcon.context)
            .load(StringEscapeUtils.unescapeJava(airlineInfo.image)).into(holder.airlineIcon)

        // pricing
        val price =
            dataSet.data.flights.departure[position].price_breakdown.total.toString() + " " +
                    dataSet.data.flights.departure[position].price_breakdown.displayed_currency
        holder.flightPrice.text = price

        // baggage
        holder.bagaggeAllawance.setBackgroundResource(
            if(isBaggageAllowance(position)) R.drawable.suitcases else R.drawable.bag)
        holder.bagagge.text = getBagaggeOptions(position)

        // flight type
        holder.flightType.text = if(dataSet.data.search_parameters.is_direct) context.getString(R.string.direct_flight)
        else context.getString(R.string.transit_flight)

        // departure and destination
        holder.departure.text = dataSet.data.flights.departure[position].segments[0].origin
        holder.destination.text = dataSet.data.flights.departure[position].segments[0].destination

        // departure and arrival time
        holder.departureTime.text = dataSet.data.flights.departure[position].segments[0].departure_datetime.time
        holder.arrivalTime.text = dataSet.data.flights.departure[position].segments[0].arrival_datetime.time

    }

    private fun getAirline(position: Int): Airline {
        val airlineCode = dataSet.data.flights.departure[position].segments[0].marketing_airline
        return dataSet.data.airlines.first { it.code == airlineCode }

    }

    private fun getBagaggeOptions(position: Int): String {
        return if (isBaggageAllowance(position)) {
            val personString = context.getString(R.string.person)
            val allowance =
                dataSet.data.flights.departure[position].infos.baggage_info.firstBaggageCollection?.get(
                    0
                )?.allowance.toString()
            val unit =
                dataSet.data.flights.departure[position].infos.baggage_info.firstBaggageCollection?.get(
                    0
                )?.unit
            "$allowance  $unit/$personString"
        } else {
            context.getString(R.string.handbag)
        }
    }

    private fun isBaggageAllowance(position: Int) =
        dataSet.data.flights.departure[position].infos.baggage_info.firstBaggageCollection != null

    override fun getItemCount() = dataSet.data.flights.departure.size

}
