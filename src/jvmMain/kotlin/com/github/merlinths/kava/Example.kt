package com.github.merlinths.kava

import com.github.merlinths.kava.validation.valid
import com.github.merlinths.kava.validator.optional

fun main() = valid {
    val (name) = parseName("Hello Kotlin!")

    println(name)
}

fun parseName(greeting: String) = optional {
    ensure (greeting) {
        isNotBlank() and endsWith("!")
    }

    val (name) = "Hello\\s([^!]*)!"
        .toRegex()
        .find(greeting)

    name.groupValues[1]
}