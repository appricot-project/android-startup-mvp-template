package ru.appricot.startuphub.auth.signin.validation

import androidx.annotation.StringRes

/**
 * Base interface for validation rules
 */
fun interface Rule<T> {
    /**
     * Validates the given value and returns an error message if invalid
     * @param value The value to validate
     * @return Error message if validation fails, null if valid
     */
    @StringRes
    fun validate(value: T): Int?
}

/**
 * Generic validator that applies multiple rules to a value
 */
class Validator<T>(private vararg val rules: Rule<T>) {

    /**
     * Validates the given value against all rules
     * @param value The value to validate
     * @return Error message from the first failed rule, or null if all rules pass
     */
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
