package ru.appricot.startuphub.auth.signup.validation

import android.util.Patterns
import ru.apprictor.startuphub.auth.R

object SignUpEmailRules {
    val notEmpty = Rule<String> { email ->
        if (email.isBlank()) {
            R.string.signup_validation_mail_empty
        } else {
            null
        }
    }

    val validFormat = Rule<String> { email ->
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            R.string.signup_validation_mail_incorrect
        } else {
            null
        }
    }
}
