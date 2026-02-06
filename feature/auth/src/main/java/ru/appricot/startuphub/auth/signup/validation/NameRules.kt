package ru.appricot.startuphub.auth.signup.validation

import ru.apprictor.startuphub.auth.R

object NameRules {
    val notEmpty = Rule<String> { name ->
        if (name.isBlank()) {
            R.string.signup_validation_name_empty
        } else {
            null
        }
    }

    val minLength = Rule<String> { name ->
        if (name.length < 2) {
            R.string.signup_validation_name_min_length
        } else {
            null
        }
    }

    val validFormat = Rule<String> { name ->
        if (!name.matches(Regex("^[a-zA-Zа-яА-ЯёЁ\\s-]+$"))) {
            R.string.signup_validation_name_invalid_characters
        } else {
            null
        }
    }
}
