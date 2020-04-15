package com.hitenderpannu.common.utils

import java.util.regex.Pattern

private const val EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})\$"
private val emailPattern by lazy { Pattern.compile(EMAIL_PATTERN) }

fun isEmailValid(email: String): Boolean {
    return emailPattern.matcher(email).matches()
}
