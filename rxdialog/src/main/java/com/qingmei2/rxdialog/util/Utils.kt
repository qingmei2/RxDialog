package com.qingmei2.rxdialog.util

object Utils {

    fun <T> validateServiceInterface(service: Class<T>) {
        if (!service.isInterface) {
            throw IllegalArgumentException("API declarations must be interfaces.")
        }
        if (service.interfaces.size > 0) {
            throw IllegalArgumentException("API interfaces must not extend other interfaces.")
        }
    }
}