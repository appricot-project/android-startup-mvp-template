package ru.appricot.startuphub.auth.signin.validation

import android.util.Patterns
import ru.apprictor.startuphub.auth.R

/**
 * Email validation rules collection
 */
object EmailRules {

    /**
     * Rule to check if email is not empty
     */
    val notEmpty = Rule<String> { email ->
        if (email.isBlank()) {
            R.string.signin_validation_mail_empty
        } else {
            null
        }
    }

    /**
     * Rule to check if email has valid format
     */
    val validFormat = Rule<String> { email ->
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            R.string.signin_validation_mail_incorrect
        } else {
            null
        }
    }
}
