package com.coinstats.common.extensions

import com.coinstats.common.utils.NumberFormatter

fun Double.toAmount(currency:String): String {
    return "${toAmount()} $currency"
}

fun Double.toAmount(): String {
    return NumberFormatter
        .formatToStringTwoZeros(this)
}