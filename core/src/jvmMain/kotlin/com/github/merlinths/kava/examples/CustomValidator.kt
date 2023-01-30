package com.github.merlinths.kava.examples

import com.github.merlinths.kava.annotations.Kava
import com.github.merlinths.kava.scope.ValidationScope
import com.github.merlinths.kava.scope.validate
import com.github.merlinths.kava.validator.Validator
import kotlin.reflect.KProperty


// Manually written
sealed interface Result<Type> {
    class Failure<Type> : Result<Type>
    data class Success<Type>(val value: Type) : Result<Type>
}

@Kava(name = "result")
class ResultValidator<Type> : Validator<Type, Result<Type>> {
    override val invalid =
        Result.Failure<Type>()

    override fun valid(value: Type) =
        Result.Success(value)

    override fun ValidationScope<*>.validate(wrapper: Result<Type>) =
        when (wrapper) {
            is Result.Success -> wrapper.value
            is Result.Failure -> fail()
        }
}

// KSP generated
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




// TODO: Create "snow" shortcut
/*
fun main2() = valid {
    val something: String by produceSomething()
    val response = `*` {
        val myResponse by receiveResponse()
        processResponse(myResponse)

        Result.Failure<String>()
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