package io.mths.kava

import io.mths.kava.condition.ensure
import io.mths.kava.result.*
import io.mths.kava.result.extensions.getValue
import io.mths.kava.result.extensions.result
import io.mths.kava.validator.extensions.getValue
import io.mths.kava.validator.extensions.optional
import io.mths.kava.validator.extensions.validate

fun main() {
    validate {
        val name: String by parseName("Hello Kotlin!")

        println("Bye $name!")
    } onFailure {
        println("Unable to parse a name!")
    } onSuccess {
        println("Everything works fine.")
    }
}

fun parseName(greeting: String) = result {
    ensure (greeting) {
        isNotBlank() and endsWith("!")
    }

    val name by "Hello\\s([^!]*)!"
        .toRegex()
        .find(greeting)

    name.groupValues[1]
}