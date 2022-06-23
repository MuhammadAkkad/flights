package com.muhammad.flights.data.model


data class FlightsModel(
    val data: Data
)

data class Data(
    val searchParameters: SearchParameters,
    val createdAt: String,
    val airlines: List<Airline>,
    val airports: List<Airport>,
    val stopCounts: List<Long>,
    val baggages: List<Long>,
    val filterBoundaries: FilterBoundaries,
    val hasViFlight: Boolean,
    val infoMessage: Any? = null,
    val searchURL: String,
    val shortSearchURL: String,
    val currencies: Currencies,
    val busSearchDataTab: Any? = null,
    val flights: FlightsClass,
    val priceHistory: PriceHistory
)

data class Airline(
    val code: String,
    val name: String,
    val slug: String,
    val image: String
)

data class Airport(
    val id: String,
    val slug: String,
    val airportName: String,
    val cityCode: String? = null,
    val cityName: String,
    val citySlug: String? = null,
    val countryCode: String,
    val countryName: String,
    val label: String? = null,
    val isCity: Boolean? = null
)

data class Currencies(
    val aed: String,
    val ars: String,
    val aud: String,
    val azn: String,
    val bdt: String,
    val bhd: String,
    val bob: String,
    val brl: String,
    val byn: String,
    val cad: String,
    val chf: String,
    val clp: String,
    val cny: String,
    val cop: String,
    val crc: String,
    val dkk: String,
    val dzd: String,
    val egp: String,
    val eur: String,
    val gbp: String,
    val hkd: String,
    val ils: String,
    val inr: String,
    val iqd: String,
    val jod: String,
    val jpy: String,
    val kgs: String,
    val krw: String,
    val kwd: String,
    val kzt: String,
    val lbp: String,
    val lkr: String,
    val mad: String,
    val mxn: String,
    val myr: String,
    val nok: String,
    val nzd: String,
    val omr: String,
    val pab: String,
    val pen: String,
    val php: String,
    val pkr: String,
    val qar: String,
    val rub: String,
    val sar: String,
    val sek: String,
    val sgd: String,
    val thb: String,
    val tnd: String,
    val currenciesTRY: String,
    val twd: String,
    val usd: String,
    val enc: Long
)

data class FilterBoundaries(
    val departurePrice: Price,
    val returnPrice: Price,
    val departureMaxDuration: Long,
    val returnMaxDuration: Long
)

data class Price(
    val min: Long,
    val max: Long
)

data class FlightsClass(
    val departure: List<DepartureElement>
)

data class DepartureElement(
    val enuid: String,
    val priceBreakdown: PriceBreakdown,
    val averagePriceBreakdown: PriceBreakdown,
    val infos: Infos,
    val providerPackages: List<Any?>,
    val segments: List<Segment>,
    val differentAirlineCount: Long
)

data class PriceBreakdown(
    val base: Double,
    val tax: Double,
    val service: Double,
    val reissueService: Long,
    val total: Double,
    val currency: Currency,
    val discount: Long,
    val displayedCurrency: DisplayedCurrency,
    val internalAssurance: Long,
    val extraFee: Double,
    val penalty: Long
)

enum class Currency {
    Try
}

enum class DisplayedCurrency {
    Tl
}

data class Infos(
    val isReservable: Boolean,
    val isPromo: Boolean,
    val duration: Duration,
    val baggageInfo: BaggageInfo,
    val isVirtualInterlining: Boolean,
    val isBusiness: Boolean,
    val activeWarning: Any? = null,
    val isMaskRequired: Boolean
)

data class BaggageInfo(
    val carryOn: CarryOn,
    val firstBaggageCollection: List<FirstBaggageCollection>? = null
)

data class CarryOn(
    val allowance: Long,
    val type: Type,
    val unit: Unit,
    val part: Long,
    val isSmall: Boolean
)

enum class Type {
    Weight
}

enum class Unit {
    Kg
}

data class FirstBaggageCollection(
    val paxType: PaxType,
    val allowance: Long,
    val part: Long,
    val unit: Unit,
    val type: Type,
    val small: Boolean
)

enum class PaxType {
    Adult
}

data class Duration(
    val day: Long,
    val hour: Long,
    val minute: Long,
    val totalMinutes: Long
)

data class Segment(
    val departureDatetime: Datetime,
    val displayDepartureDatetime: DisplayDatetime,
    val arrivalDatetime: Datetime,
    val displayArrivalDatetime: DisplayDatetime,
    val segmentClass: String,
    val flightNumber: String,
    val origin: String,
    val destination: String,
    val marketingAirline: String,
    val operatingAirline: String,
    val availableSeats: Long,
    val originTerminal: String? = null,
    val destinationTerminal: String? = null,
    val segmentDelay: Duration? = null,
    val duration: Duration,
    val isTrain: Boolean,
    val segmentAttributes: SegmentAttributes,
    val isVirtualInterlining: Boolean
)

data class Datetime(
    val date: Date,
    val time: String,
    val timestamp: Long
)

enum class Date {
    The28062022,
    The29062022
}

enum class DisplayDatetime {
    The28Haziran2022SalU0131,
    The29Haziran2022U00C7ArU015Famba
}

data class SegmentAttributes(
    val freeMeal: Boolean,
    val seatPitch: String? = null,
    val airplaneBrand: AirplaneBrand? = null,
    val entertainment: Entertainment? = null,
    val wifi: Long? = null,
    val seatPlan: SeatPlan? = null
)

enum class AirplaneBrand {
    AirbusA319,
    AirbusA320,
    AirbusA321,
    AirbusA330200,
    Boeing737800,
    Boeing777300ER
}

enum class Entertainment {
    Overhead,
    Personal
}

enum class SeatPlan {
    The242,
    The33,
    The333
}

data class PriceHistory(
    val departure: PriceHistoryDeparture
)

data class PriceHistoryDeparture(
    val previousDayPrice: Long,
    val nextDayPrice: Long
)

data class SearchParameters(
    val requestID: String,
    val provider: Any? = null,
    val origin: Airport,
    val destination: Airport,
    val origins: List<Airport>,
    val destinations: List<Airport>,
    val departureDates: List<String>,
    val departureDate: String,
    val displayDepartureDate: String,
    val displayDepartureDates: List<String>,
    val returnDate: Any? = null,
    val displayReturnDate: Any? = null,
    val adult: Long,
    val senior: Long,
    val student: Long,
    val child: Long,
    val infant: Long,
    val passengerCount: Long,
    val passengerServisableCount: Long,
    val version: Long,
    val isOneWay: Boolean,
    val isDomestic: Boolean,
    val isDirect: Boolean,
    val isRefundable: Boolean,
    val flightClass: String
)
