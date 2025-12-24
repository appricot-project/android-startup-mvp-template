package ru.appricot.startuphub.core.network

import javax.inject.Qualifier

@Qualifier
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl
