package io.mths.kava.result

import io.mths.kava.ValidationScope
import io.mths.kava.Validator
import io.mths.kava.scope.validate
import kotlin.reflect.KProperty

// Manually written
sealed interface Result<Type> {
    class Failure<Type> : Result<Type>
    data class Success<Type>(val value: Type) : Result<Type>
}

// KSP generated
/*
fun <Type> result(
    block: ValidationScope<Type>.() -> Type
) = validate(ResultValidator(), block)

context (ValidationScope<*>)
operator fun <Type> Result<Type>.getValue(
    thisRef: Any?,
    property: KProperty<*>
): Type =  with(this@ValidationScope) {
    this@getValue.`*`
}

context (ValidationScope<*>)
val <Type> Result<Type>.`*`: Type
    get() = with(this@ValidationScope) {
        ResultValidator<Type>().run {
            this@ValidationScope.validate(this@`*`)
        }
    }


*/

// TODO: Create "snow" shortcut
/*
fun main2() = valid {
    val something: String by produceSomething()
    val response = `*` {
        val myResponse by receiveResponse()
        processResponse(myResponse)

        io.mths.kava.result.Result.Failure<String>()
    }

    val somethingElse = `*` { produceSomething() }

    processResponse(
        `*` { produceSomething() }
    )

    //val another = produceSomething() `*` ("Producing failed!")
}

fun processResponse(response: String) {
}

fun produceSomething() = result {
    val greeting by "Hello"

    "$greeting Custom Datatype!"
}

fun receiveResponse() = nullable {
    "Successful"
}*/