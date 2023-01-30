package com.github.merlinths.kava.examples

import com.github.merlinths.kava.ensure
import com.github.merlinths.kava.validator.getValue
import com.github.merlinths.kava.validator.onFailure
import com.github.merlinths.kava.validator.validate

/*
    Instead of using Prefix ( function ) notation `*`{ ... }
    Use Postfix ( property ) notation { ... }.`*` to infer types better.

    ( see lambda return type inference )
 */


fun main() {
    validate {
        //val name: String by parseName("Hello Kotlin!")

        println("Ok")
        //println("Bye $name!")
    } onFailure {
        println("Unable to parse a name!")
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
/*
fun getName(): String? {
    return "Kava"
}

fun m3ain() {
    validShowcase()
    optionalShowcase()
}

fun validShowcase() {
    validate {
        val name by getName()

        println("Hello $name!")
    } onSuccess {
        println("Success is all around us!")
    } onFailure {
        println("Operation failed!")
    }
}
fun optionalShowcase() {
    data class Person(val name: String, val age: Int)

    val maybePerson = optional {
        fun createPerson() =
            Optional.of(Person("Marko", 23))

        val person = createPerson()

        val validPerson = `*`(::createPerson)
        `*` { person }
    }

    validate {
        val person by maybePerson

        println(person)
    }

    maybePerson.ifPresent(::println)
}
*/