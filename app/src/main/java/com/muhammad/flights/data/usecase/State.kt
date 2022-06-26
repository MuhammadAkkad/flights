package com.muhammad.flights.data.usecase

data class State<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): State<T> {
            return State(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): State<T> {
            return State(Status.ERROR, null, msg)
        }

    }
}

enum class Status {
    SUCCESS,
    ERROR
}
