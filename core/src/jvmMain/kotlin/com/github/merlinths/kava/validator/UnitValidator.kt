package com.github.merlinths.kava.validator

import com.github.merlinths.kava.scope.ValidationScope

class UnitValidator : Validator<Unit, ValidationResult> {
    override val invalid = ValidationResult.Failure

    override fun valid(value: Unit) = ValidationResult.Success


    override fun ValidationScope<*>.validate(wrapper: ValidationResult) =
        Unit
}

enum class ValidationResult {
    Success,
    Failure
}

fun ValidationResult.failed() =
    !succeeded()

fun ValidationResult.succeeded() =
    this == ValidationResult.Success

infix fun ValidationResult.onFailure(block: () -> Unit) = also {
    if (it.failed()) {
        block()
    }
}

infix fun ValidationResult.onSuccess(block: () -> Unit) = also {
    if (it.succeeded()) {
        block()
    }
}

fun onlyValidate(
    block: ValidationScope<*>.() -> Unit
) {
    validate(block)
}

fun validate(
    block: ValidationScope<*>.() -> Unit
): ValidationResult =
    com.github.merlinths.kava.scope.validate(UnitValidator(), block)