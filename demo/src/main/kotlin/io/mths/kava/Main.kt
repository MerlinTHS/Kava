package io.mths.kava

import io.mths.kava.result.Result
import io.mths.kava.result.onFailure
import io.mths.kava.result.result
import io.mths.kava.validator.getValue
import io.mths.kava.validator.nullable
import io.mths.kava.validator.validate

fun main() {
    validate {
        val name by parseName("Hello Kotlin!")
        println("Bye $name!")

        val status = result {
            ensure (name) {
                isNotBlank()
            }

            "Ok"
        }

        ensure (status !is Result.Success)
        println("Everything works fine.")
    } onFailure {
        println("Unable to parse a name!")
    }
}

fun parseName(greeting: String) = nullable {
    ensure(greeting) {
        isNotBlank() and endsWith("!")
    }

    val name by "Hello\\s([^!]*)!"
        .toRegex()
        .find(greeting)

    name.groupValues[1]
}