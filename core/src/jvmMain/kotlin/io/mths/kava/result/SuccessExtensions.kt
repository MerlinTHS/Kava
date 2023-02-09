package io.mths.kava.result

fun ValidationResult.succeeded() =
    this == ValidationResult.Success

infix fun ValidationResult.onSuccess(block: () -> Unit) = also {
    if (it.succeeded()) {
        block()
    }
}