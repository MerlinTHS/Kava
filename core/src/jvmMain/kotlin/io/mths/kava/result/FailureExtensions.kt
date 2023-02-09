package io.mths.kava.result

fun ValidationResult.failed() =
    !succeeded()

infix fun ValidationResult.onFailure(block: () -> Unit) = also {
    if (it.failed()) {
        block()
    }
}