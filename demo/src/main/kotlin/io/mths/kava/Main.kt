package io.mths.kava

import io.mths.kava.result.nested
import io.mths.kava.result.result
import io.mths.kava.validator.nullable
import io.mths.kava.validator.onFailure
import io.mths.kava.validator.validate
import java.util.*

/*
    Instead of using Prefix ( function ) notation `*`{ ... }
    Use Postfix ( property ) notation { ... }.`*` to infer types better.

    ( see lambda return type inference )
 */


fun main() {
    validate {
        //val name: String by parseName("Hello Kotlin!")
        /*val result = nested<Int> {
            println("Before fail")
            fail()
            println("After fail")
            Optional.of("Hello Kotlin!")
        }*/
        val result = result { // IntelliJ should warn you!
            fail()
            "Hello Kava!"
        }

        nullable {
            "Hello Kava!"
        }

        println("Result is $result")

        println("Ok")
        //println("Bye $name!")
    } onFailure {
        println("Unable to parse a name!")
    }
}
/*
fun parseName(greeting: String) = result {
    ensure(greeting) {
        isNotBlank() and endsWith("!")
    }

    val name by "Hello\\s([^!]*)!"
        .toRegex()
        .find(greeting)

    name.groupValues[1]
}*/
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