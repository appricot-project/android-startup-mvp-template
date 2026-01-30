package ru.appricot.startuphub.auth.signin.validation

import android.util.Patterns

/**
 * Email validation rules collection
 */
object EmailRules {

    /**
     * Rule to check if email is not empty
     */
    val notEmpty = Rule<String> { email ->
        if (email.isBlank()) {
            "Email cannot be empty"
        } else {
            null
        }
    }

    /**
     * Rule to check if email has valid format
     */
    val validFormat = Rule<String> { email ->
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Please enter a valid email address"
        } else {
            null
        }
    }

    /**
     * Rule to check if email has minimum length (3 characters for basic format)
     */
    val minLength = Rule<String> { email ->
        if (email.length < 3) {
            "Email must be at least 3 characters long"
        } else {
            null
        }
    }

    /**
     * Rule to check if email contains @ symbol
     */
    val containsAtSymbol = Rule<String> { email ->
        if (!email.contains("@")) {
            "Email must contain @ symbol"
        } else {
            null
        }
    }
}
