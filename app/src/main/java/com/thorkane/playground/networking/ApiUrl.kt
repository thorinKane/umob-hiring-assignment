package com.thorkane.playground.networking

@Target(AnnotationTarget.CLASS)
annotation class ApiUrl (
    val url: String
)
