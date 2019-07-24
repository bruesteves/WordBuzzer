package com.wordbuzzer.ui.utils

class ViewModelResponse<T>(var data: T?, var status: Status, var messageError: String?) {

    enum class Status {
        ERROR, SUCCESS
    }

    companion object {
        fun <T> success(data: T?): ViewModelResponse<T> {
            return ViewModelResponse(data, Status.SUCCESS, null)
        }

        fun <T> error(e: Throwable?): ViewModelResponse<T> {
            return ViewModelResponse(null, Status.ERROR, e?.localizedMessage)
        }
    }
}