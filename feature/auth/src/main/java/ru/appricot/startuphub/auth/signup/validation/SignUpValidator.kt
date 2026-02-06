package ru.appricot.startuphub.auth.signup.validation

import androidx.annotation.StringRes

fun interface Rule<T> {
    @StringRes
    fun validate(value: T): Int?
}

class Validator<T>(private vararg val rules: Rule<T>) {
    @StringRes
    fun validate(value: T): Int? {
        for (rule in rules) {
            val error = rule.validate(value)
            if (error != null) {
                return error
            }
        }
        return null
    }
}
