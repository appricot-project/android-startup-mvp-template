package ru.appricot.startuphub.auth.signin

import android.net.Uri

interface AuthConfig {
    fun clientId(): String
    fun clientSecret(): String
    fun callbackUri(): Uri
    fun authUri(): Uri
    fun tokenUri(): Uri
    fun responseType(): String
    fun scope(): String
}
