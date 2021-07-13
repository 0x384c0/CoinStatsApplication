package com.coinstats.app.util.extensions

import com.coinstats.app.util.NumberFormatter

fun Double.toAmount(currency:String): String {
    return "${toAmount()} $currency"
}

fun Double.toAmount(): String {
    return NumberFormatter
        .formatToStringTwoZeros(this)
}