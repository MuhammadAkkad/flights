package com.muhammad.flights.data.model

data class Infos(
    val active_warning: Any,
    val baggage_info: BaggageInfo,
    val duration: Duration,
    val is_business: Boolean,
    val is_mask_required: Boolean,
    val is_promo: Boolean,
    val is_reservable: Boolean,
    val is_virtual_interlining: Boolean
)